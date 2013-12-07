package com.example;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Kobil
 * Date: 12/7/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseHTML {
        private TagNode rootNode;

        //Конструктор
        public ParseHTML(URL htmlPage) throws IOException {
            //Создаём объект HtmlCleaner
            HtmlCleaner htmlCleaner = new HtmlCleaner();
            //Загружаем html код сайта
            rootNode = htmlCleaner.clean(htmlPage);
        }

        public List<TagNode> getLinksByClass() throws XPatherException {
            //Выбираем все ссылки
            List<TagNode> linkElements = new ArrayList <TagNode>();
            Object[] tagNodes = rootNode.evaluateXPath(".//h3/a");
            for(Object tagNode : tagNodes){
                linkElements.add((TagNode)tagNode);
            }
            return linkElements;
        }
}
