package click.kobaken.smartranking.entity;

import java.util.ArrayList;
import java.util.List;

public class Idol {
    public String id;
    public String name;
    public String description;
    public int numVote;
    public String imageUrl;

    public static Idol createMock(String id) {
        Idol idol = new Idol();
        idol.id = id;
        idol.name = "三森すずこ";
        idol.description = "声優界のレインメーカー！\nみもりん！大好きっ！";
        idol.numVote = 628;
        idol.imageUrl = id;
        return idol;
    }

    public static List<Idol> createMocks(int num) {
        List<Idol> idols = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            idols.add(createMock(String.valueOf(i)));
        }
        return idols;
    }
}
