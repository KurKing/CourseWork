package com.kpi.it01.kurkin.coursework.controllers.factories;

import com.kpi.it01.kurkin.coursework.controllers.decorators.*;
import com.kpi.it01.kurkin.coursework.services.TenderService;
import com.kpi.it01.kurkin.coursework.services.UserService;

public class ProcessRequestDecoratorFactory {

    private UserService userService;
    private TenderService tenderService;

    public ProcessRequestDecoratorFactory(UserService userService, TenderService tenderService) {
        this.userService = userService;
        this.tenderService = tenderService;
    }

    public ProcessRequestDecorator getDecoratorForPath(String path) {
        switch (path) {
            case "/login":
                return new LoginProcessRequestDecorator(userService);
            case "/signup":
                return new SignUpProcessRequestDecorator(userService);
            case "/createTender":
            case "/newTender":
                return new TenderCreateProcessRequestDecorator(tenderService);
            case "/createOffer":
            case "/newOffer":
                return new OfferCreateProcessRequestDecorator(tenderService);
            case "/logout":
                return new LogOutProcessRequestDecorator();
            case "/setStatus":
                return new SetStatusProcessRequestDecorator(tenderService);
            case "/search":
                return new TenderWithNameProcessRequestDecorator(tenderService);
            case "/deleteTender":
                return new DeleteProcessRequestDecorator(tenderService);
            case "/tender":
                return new TenderWithIdProcessRequestDecorator(tenderService);
            default:
                return new TenderListProcessRequestDecorator(tenderService);
        }
    }

}
