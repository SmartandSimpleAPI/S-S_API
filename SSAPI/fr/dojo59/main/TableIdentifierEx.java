package fr.dojo59.main;

import fr.dojo59.api.provided.DataBaseAPI.utilsDb.table.TableIdentifier;


public class TableIdentifierEx extends TableIdentifier {

    private String identifier;
    private String str$98$;
    private double money1;
    private double money2;
    private int winnerEvent;

    public TableIdentifierEx(String identifier, String str, double money1, double money2, int winnerEvent) {
        this.identifier = identifier;
        this.str$98$ = str;
        this.money1 = money1;
        this.money2 = money2;
        this.winnerEvent = winnerEvent;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
