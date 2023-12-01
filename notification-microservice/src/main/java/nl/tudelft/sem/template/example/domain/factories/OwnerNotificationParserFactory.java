package nl.tudelft.sem.template.example.domain.factories;

public class OwnerNotificationParserFactory extends ParserFactory{
    @Override
    public Parser createParser(){
        return new OwnerNotificationParser();
    }
}
