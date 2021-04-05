package com.kpi.it01.kurkin.coursework.controllers.factories;

import com.kpi.it01.kurkin.coursework.controllers.strategies.*;
import com.kpi.it01.kurkin.coursework.services.TenderService;
import com.kpi.it01.kurkin.coursework.services.UserService;

public class ProcessRequestStrategiesFactory {

    private UserService userService;
    private TenderService tenderService;

    public ProcessRequestStrategiesFactory(UserService userService, TenderService tenderService) {
        this.userService = userService;
        this.tenderService = tenderService;
    }

    public ProcessRequestStrategy getStrategyForPath(String path) {
        switch (path) {
            case "/login":
                return new LoginProcessRequestStrategy(userService);
            case "/signup":
                return new SignUpProcessRequestStrategy(userService);
            case "/createTender":
            case "/newTender":
                return new TenderCreateProcessRequestStrategy(tenderService);
            case "/createOffer":
            case "/newOffer":
                return new OfferCreateProcessRequestStrategy(tenderService);
            case "/logout":
                return new LogOutProcessRequestStrategy();
            case "/setStatus":
                return new SetStatusProcessRequestStrategy(tenderService);
            case "/search":
                return new TenderWithNameProcessRequestStrategy(tenderService);
            case "/deleteTender":
                return new DeleteProcessRequestStrategy(tenderService);
            case "/tender":
                return new TenderWithIdProcessRequestStrategy(tenderService);
            default:
                return new TenderListProcessRequestStrategy(tenderService);
        }
    }

}
