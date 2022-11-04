package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.entity.CardStat;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import ci.ashamaz.hwheroku.service.CardStatService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public class TripletComponent extends VerticalLayout {
    private static final String DEFAULT_IMG = "static/tarot.jpeg";
    private CardStatService cardStatService;
    private ComboBox<TarotEnum> firstTeam;
    private ComboBox<TarotEnum> draw;
    private ComboBox<TarotEnum> secondTeam;
    private Image image1;
    private Image imageDraw;
    private Image image2;
    private Text text1;
    private Text textDraw;
    private Text text2;
    @Setter
    private Runnable onComboChange;

    public TripletComponent(CardStatService cardStatService) {
        this.cardStatService = cardStatService;
        HorizontalLayout cardChooserLayout = new HorizontalLayout();
        cardChooserLayout.setPadding(false);
        cardChooserLayout.setSpacing(false);
        cardChooserLayout.setAlignItems(Alignment.BASELINE);
        add(cardChooserLayout);

        VerticalLayout div1 = new VerticalLayout();
        div1.addClassName("triplet-card");
        firstTeam = new ComboBox<>("П1");
        image1 = new Image();
        image1.setWidth("200px");
        image1.setHeight("300px");
        image1.setSrc(DEFAULT_IMG);
        text1 = new Text("");
        div1.add(firstTeam, image1, text1);

        VerticalLayout div2 = new VerticalLayout();
        div2.addClassName("triplet-card");
        draw = new ComboBox<>("Х");
        imageDraw = new Image();
        imageDraw.setWidth("200px");
        imageDraw.setHeight("300px");
        imageDraw.setSrc(DEFAULT_IMG);
        textDraw = new Text("");
        div2.add(draw, imageDraw, textDraw);

        VerticalLayout div3 = new VerticalLayout();
        div3.addClassName("triplet-card");
        secondTeam = new ComboBox<>("П2");
        image2 = new Image();
        image2.setWidth("200px");
        image2.setHeight("300px");
        image2.setSrc(DEFAULT_IMG);
        text2 = new Text("");
        div3.add(secondTeam, image2, text2);

        cardChooserLayout.add(div1, div2, div3);
        Arrays.asList(firstTeam, draw, secondTeam).forEach(e -> {
            e.setItems(TarotEnum.values());
            e.setItemLabelGenerator(it -> it.getDisplayName() + " " + it.name());
        });

        firstTeam.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                image1.setSrc(DEFAULT_IMG);
                text1.setText("");
            } else {
                image1.setSrc("static/img/" + e.getValue().getLink());
                text1.setText(getDescription(e.getValue()));
            }
            if (onComboChange != null) {
                onComboChange.run();
            }
        });

        draw.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                imageDraw.setSrc(DEFAULT_IMG);
                textDraw.setText("");
            } else {
                imageDraw.setSrc("static/img/" + e.getValue().getLink());
                textDraw.setText(getDescription(e.getValue()));
            }
            if (onComboChange != null) {
                onComboChange.run();
            }
        });

        secondTeam.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                image2.setSrc(DEFAULT_IMG);
                text2.setText("");
            } else {
                image2.setSrc("static/img/" + e.getValue().getLink());
                text2.setText(getDescription(e.getValue()));
            }
            if (onComboChange != null) {
                onComboChange.run();
            }
        });

    }

    public boolean areCardsChosen() {
        return firstTeam.getValue() != null && draw.getValue() != null && secondTeam.getValue() != null;
    }

    public void cleanTriplet() {
        firstTeam.setValue(null);
        draw.setValue(null);
        secondTeam.setValue(null);
        image1.setSrc(DEFAULT_IMG);
        imageDraw.setSrc(DEFAULT_IMG);
        image2.setSrc(DEFAULT_IMG);
        text1.setText("");
        textDraw.setText("");
        text2.setText("");
    }

    private String getDescription(TarotEnum value) {
        CardStat cardStat = cardStatService.getCardStat(value);
        if (cardStat != null) {
            return cardStat.toString();
        } else {
            return "No stat";
        }
    }

}
