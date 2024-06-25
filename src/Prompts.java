public enum Prompts {
    CUR_WAGE("תשלום עבור טיפול"),
    WANTED_WAGE("תשלום רצוי עבור טיפול"),
    CUR_NUM_OF_CLIENTS("מספר לקוחות"),
    WANTED_NUM_OF_CLIENTS("מספר לקוחות רצוי"),

    ELECTRICITY("חשבון חשמל - 4 חודשים"),
    WATER("חשבון מים - 4 חודשים"),
    INTERNET("חשבון אינטרנט - 4 חודשים"),
    RATES("חשבון ארנונה - 4 חודשים"),

    RENT("שכירות קליניקה - רבעוני"),
    PRODUCTS("עלות חומרים - רבעוני"),
    EDUCATION("עלות השתלמויות - רבעוני"),
    ADVISION("עלות הדרכות - רבעוני"),
    RETIREMENT_FEE("הפרשה לפנסיה - חודשי"),

    TAX_FACTOR("אחוז מס"),
    PAID_CANCELS_AMOUNT("כמות ביטולים של מטופלים - רבעוני"),
    SELF_CANCELS_AMOUNT("כמות ביטולים יזומים / עקב מועדים וחגים - רבעוני"),
    CANCELING_FEE_FACTOR("שכר חלקי עבור ביטול");


    public final String label;

    Prompts(String label) {
        this.label = label;
    }
    }
