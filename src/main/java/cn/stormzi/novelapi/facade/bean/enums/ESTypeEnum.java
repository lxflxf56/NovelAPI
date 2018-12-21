package cn.stormzi.novelapi.facade.bean.enums;

public enum ESTypeEnum {
    Insert_Page(1),
    Insert_Chapters(2),
    Update_Page(3),
    Update_Chapters(4),
    Insert_Chapters_List(5),
    Insert_Chapters_Bean(6)
    ;

    private int index;

    ESTypeEnum(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static ESTypeEnum getEnum(int index){
        ESTypeEnum[] values = values();
        for (ESTypeEnum typeEnum:values){
            if (typeEnum.index==index){
                return typeEnum;
            }
        }
        return null;
    }
}
