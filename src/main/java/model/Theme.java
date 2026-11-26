package model;

import java.awt.Color;

public class Theme {

	public final Color entete;
	public final Color bordure;
	public final Color caseBase;
	public final Color caseClair;
	public final Color caseSombre;
	public final Color videBase;
	public final Color videClair;
	public final Color videSombre;
	public final Color dangerBase;
	public final Color dangerClair;
	public final Color dangerSombre;
	public final boolean flammes;

	private Theme(Color entete, Color bordure,
			Color caseBase, Color caseClair, Color caseSombre,
			Color videBase, Color videClair, Color videSombre,
			Color dangerBase, Color dangerClair, Color dangerSombre,
			boolean flammes) {
		this.entete = entete;
		this.bordure = bordure;
		this.caseBase = caseBase;
		this.caseClair = caseClair;
		this.caseSombre = caseSombre;
		this.videBase = videBase;
		this.videClair = videClair;
		this.videSombre = videSombre;
		this.dangerBase = dangerBase;
		this.dangerClair = dangerClair;
		this.dangerSombre = dangerSombre;
		this.flammes = flammes;
	}

	public static final Theme FACILE = new Theme(
		new Color(18, 32, 26), new Color(26, 44, 36),
		new Color(72, 142, 104), new Color(104, 182, 138), new Color(48, 100, 74),
		new Color(58, 86, 72), new Color(80, 110, 94), new Color(42, 66, 54),
		new Color(170, 40, 40), new Color(215, 85, 75), new Color(110, 22, 22),
		false);

	public static final Theme MOYEN = new Theme(
		new Color(36, 28, 16), new Color(48, 38, 22),
		new Color(150, 118, 62), new Color(190, 154, 92), new Color(108, 82, 40),
		new Color(88, 72, 50), new Color(112, 94, 68), new Color(66, 54, 36),
		new Color(170, 40, 40), new Color(215, 85, 75), new Color(110, 22, 22),
		false);

	public static final Theme DIFFICILE = new Theme(
		new Color(40, 6, 8), new Color(64, 8, 10),
		new Color(210, 28, 28), new Color(250, 70, 55), new Color(140, 14, 16),
		new Color(104, 28, 30), new Color(136, 46, 46), new Color(76, 18, 20),
		new Color(200, 160, 40), new Color(235, 200, 90), new Color(130, 100, 20),
		true);

	private static final Theme[] THEMES = {FACILE, MOYEN, DIFFICILE};

	public static Theme pour(int niveau) {
		return THEMES[Math.max(0, Math.min(niveau, THEMES.length - 1))];
	}
}