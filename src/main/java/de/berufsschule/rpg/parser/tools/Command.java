package de.berufsschule.rpg.parser.tools;

public enum Command {
    GAMENAME, ITEMS, ENDITEMS, PAGES, ENDPAGES, SKILLS, ENDSKILLS, ROUNDTHIRST, ROUNDEXP, ROUNDHUNGER, DRINK,
    FOOD, MEDICINE, DESCRIPTION, NAME, SIMPLEITEM, UNUSEABLE, THIRST, STORYTEXT, STARTPAGE, PAGE, LEARNSKILL,
    HUNGER, HEALTH, GIVE, XP, DECISIONS, ENDDECISIONS, DEATHPAGE, CHECKPOINT, ADDSKILLPOINTS, USE, TEXT,
    SKILLSUCCESSLVL, SKILLMINLVL, QUESTION, MAINGOTO, ALTGOTO, DECISION, CHANCE, ANSWER, ALTANSWER, REQUIREDSKILL,
    SKILL;

    public String getCommand() {
        return "#" + this.name();
    }
}
