package com.iis.soft;

import com.iis.soft.services.FileService;
import org.apache.log4j.Logger;

/**
 * Hello world!
 */
public class CommandController {

    final static Logger logger = Logger.getLogger(CommandController.class);

    public static void main(String[] args) {

        CommandController commandController = new CommandController();

        if (args[0] == null || args[1] == null) {
            System.err.println("Need args!");
            logger.equals("args is empty");
            return;
        }

        switch (args[0]) {
            case "sync":
                commandController.sync(args[1]);
                break;
            case "export":
                commandController.export(args[1]);
                break;
            default:
                System.err.println("not valid args");
                logger.error("not valid args");
                break;
        }

    }

    private void sync(String filename) {
        new FileService().syncDbWithXml(filename);
    }

    private void export(String filename) {
        new FileService().fromDbToXml(filename);
    }

}
