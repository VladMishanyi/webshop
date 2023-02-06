package org.example;

import org.example.tasks.ParserTask;
import org.example.util.Loader;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Loader.parseArgs(args);

        ParserTask parserTask = new ParserTask();
        parserTask.count();
    }
}
