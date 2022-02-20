package com.example.triviaapp.model;

public enum QuestionCategory {
    GENERAL_KNOWLEDGE(9, "General Knowledge"),
    ENTERTAINMENT_BOOKS(10, "Entertainment: Books"),
    ENTERTAINMENT_FILM(11, "Entertainment: Film"),
    ENTERTAINMENT_MUSIC(12, "Entertainment: Music"),
    ENTERTAINMENT_MUSICAL_AND_THEATRES(13, "Entertainment: Musicals & Theatres"),
    ENTERTAINMENT_TELEVISION(14, "Entertainment: Television"),
    ENTERTAINMENT_VIDEO_GAMES(15, "Entertainment: Video Games"),
    ENTERTAINMENT_BOARD_GAMES(16, "Entertainment: Board Games"),
    SCIENCE_NATURE(17, "Science & Nature"),
    SCIENCE_COMPUTERS(18, "Science: Computers"),
    SCIENCE_MATH(19, "Science: Mathematics"),
    MYTHOLOGY(20, "Mythology"),
    SPORTS(21, "Sports"),
    GEOGRAPHY(22, "Geography"),
    HISTORY(23, "History"),
    POLITICS(24, "Politics"),
    ART(25, "Art"),
    CELEBRITIES(26, "Celebrities"),
    ANIMALS(27, "Animals"),
    VEHICLES(28, "Vehicles"),
    ENTERTAINMENT_COMICS(29, "Entertainment: Comics"),
    SCIENCE_GADGETS(30, "Science: Gadgets"),
    ENTERTAINMENT_JAPANESE_ANIME_MANGA(31, "Entertainment: Japanese Anime & Manga"),
    ENTERTAINMENT_CARTOON_ANIMATIONS(32, "Entertainment: Cartoon & Animations");

    private int id;
    private String label;

    QuestionCategory(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static QuestionCategory getQuestionCategoryFromLabel(String label) {
        switch (label) {
            case "Entertainment: Books": return ENTERTAINMENT_BOOKS;
            case "Entertainment: Film": return ENTERTAINMENT_FILM;
            case "Entertainment: Music": return ENTERTAINMENT_MUSIC;
            case "Entertainment: Musicals & Theatres": return ENTERTAINMENT_MUSICAL_AND_THEATRES;
            case "Entertainment: Television": return ENTERTAINMENT_TELEVISION;
            case "Entertainment: Video Games": return ENTERTAINMENT_VIDEO_GAMES;
            case "Entertainment: Board Games": return ENTERTAINMENT_BOARD_GAMES;
            case "Science & Nature": return SCIENCE_NATURE;
            case "Science: Computers": return SCIENCE_COMPUTERS;
            case "Science: Mathematics": return SCIENCE_MATH;
            case "Mythology": return MYTHOLOGY;
            case "Sports": return SPORTS;
            case "Geography": return GEOGRAPHY;
            case "History": return HISTORY;
            case "Politics": return POLITICS;
            case "Art": return ART;
            case "Celebrities": return CELEBRITIES;
            case "Animals": return ANIMALS;
            case "Vehicles": return VEHICLES;
            case "Entertainment: Comics": return ENTERTAINMENT_COMICS;
            case "Science: Gadgets": return SCIENCE_GADGETS;
            case "Entertainment: Japanese Anime & Manga": return ENTERTAINMENT_JAPANESE_ANIME_MANGA;
            case "Entertainment: Cartoon & Animations": return ENTERTAINMENT_CARTOON_ANIMATIONS;
            default: return GENERAL_KNOWLEDGE;
        }
    }

    public static QuestionCategory getQuestionCategoryFromId(int id) {
        switch (id) {
            case 10: return ENTERTAINMENT_BOOKS;
            case 11: return ENTERTAINMENT_FILM;
            case 12: return ENTERTAINMENT_MUSIC;
            case 13: return ENTERTAINMENT_MUSICAL_AND_THEATRES;
            case 14: return ENTERTAINMENT_TELEVISION;
            case 15: return ENTERTAINMENT_VIDEO_GAMES;
            case 16: return ENTERTAINMENT_BOARD_GAMES;
            case 17: return SCIENCE_NATURE;
            case 18: return SCIENCE_COMPUTERS;
            case 19: return SCIENCE_MATH;
            case 20: return MYTHOLOGY;
            case 21: return SPORTS;
            case 22: return GEOGRAPHY;
            case 23: return HISTORY;
            case 24: return POLITICS;
            case 25: return ART;
            case 26: return CELEBRITIES;
            case 27: return ANIMALS;
            case 28: return VEHICLES;
            case 29: return ENTERTAINMENT_COMICS;
            case 30: return SCIENCE_GADGETS;
            case 31: return ENTERTAINMENT_JAPANESE_ANIME_MANGA;
            case 32: return ENTERTAINMENT_CARTOON_ANIMATIONS;
            default: return GENERAL_KNOWLEDGE;
        }
    }
}
