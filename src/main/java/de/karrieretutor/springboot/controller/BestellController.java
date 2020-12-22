package de.karrieretutor.springboot.controller;

import de.karrieretutor.springboot.domain.Bestellung;
import de.karrieretutor.springboot.domain.Kunde;
import de.karrieretutor.springboot.domain.Warenkorb;
import de.karrieretutor.springboot.service.BestellService;
import de.karrieretutor.springboot.service.EmailService;
import de.karrieretutor.springboot.service.KundenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static de.karrieretutor.springboot.Const.*;

@Controller
public class BestellController {
    @Autowired
    BestellService bestellService;
    @Autowired
    KundenService kundenService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    EmailService emailService;

    @ModelAttribute(CART)
    public Warenkorb getInitializedWarenkorb(HttpSession session) {
        return (Warenkorb) session.getAttribute(CART);
    }

    @GetMapping("/checkout")
    public String zurKasse(Model model, HttpSession session) {
        Kunde kunde = (Kunde)session.getAttribute(CUSTOMER);
        if (kunde == null) {
            kunde = new Kunde();
        }
        Bestellung bestellung = new Bestellung();
        bestellung.setKunde(kunde);
        model.addAttribute(ORDER, bestellung);
        return "checkout";
    }

    @PostMapping("/bestellen")
    public String bestellen(@Valid Bestellung bestellung,
                            BindingResult result,
                            RedirectAttributes redirect,
                            Locale locale,
                            HttpSession session) {
        if (result.hasErrors()) {
            // When is it called?
            System.out.println("BestellController: result.hasErrors()");
            return "checkout";
        }

        Kunde kunde = bestellung.getKunde();

        if (!kunde.validiereZahlungsart(result)) {
            return "checkout";
        };

        String message = messageSource.getMessage("order.failure", null, locale);
        boolean istNeuerKunde = (kunde.getId() == null);
        Bestellung neueBestellung = bestellService.speichere(bestellung, istNeuerKunde);
        kunde.setSprache(locale.getLanguage());
        // TODO: Kundensprache berücksichtigen
        if (neueBestellung != null) {
            // Email versenden
            emailService.bestellungBestaetigung(neueBestellung);

            message = messageSource.getMessage("order.success", null, locale);
            Warenkorb warenkorb = getInitializedWarenkorb(session);
            if (warenkorb != null)
                warenkorb.getProdukte().clear();
        }
        redirect.addFlashAttribute(MESSAGE, message);
        return "redirect:/bestellung/" + neueBestellung.getId();
    }

    @GetMapping("/bestellung/{id}")
    public String bestellung(@PathVariable Long id, Model model, HttpSession session) {
        Kunde kunde = (Kunde)session.getAttribute(CUSTOMER);
        if (kunde != null) {
            Bestellung bestellung = bestellService.lade(kunde.getId(), id);
            if (bestellung == null) {
                bestellung = new Bestellung();
                model.addAttribute(MESSAGE, "No order found for ID: " + id);
            }
            model.addAttribute(ORDER, bestellung);
        }
        return "order";
    }

    @GetMapping("/bestellungen")
    public String bestellungen(Model model, HttpSession session) {
        Kunde kunde = (Kunde)session.getAttribute(CUSTOMER);
        if (kunde != null) {
            List<Bestellung> bestellungen = bestellService.bestellungenVonKunde(kunde.getId());
            model.addAttribute(ORDERS, bestellungen);
        }
        return "orders";
    }


}
