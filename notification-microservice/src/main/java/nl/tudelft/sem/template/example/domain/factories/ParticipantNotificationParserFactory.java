package nl.tudelft.sem.template.example.domain.factories;

public class ParticipantNotificationParserFactory extends ParserFactory{
    @Override
    public Parser createParser(){
        return new ParticipantNotificationParser();
    }
}
