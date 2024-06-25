public enum DataTypes {
    INCOME("הכנסות צפויות", 0),
    BILLS("חשבונות", 1),
    EXPENSES("הוצאות", 2),
    OFFSET("קיזוזים", 3);

    public final String title;
    public final int index;

    DataTypes(String title, int i) {
        this.title = title;
        this.index = i;
    }
}