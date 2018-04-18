package jp.go.aist.rtm.systemeditor.ui.editor.figure;

import jp.go.aist.rtm.systemeditor.ui.editor.editpart.ECEditPart;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.PointList;

/**
 * ECの Figureを表します。
 */
public class ECFigure extends ComponentChildFigure {

	/** ECの描画テンプレート */
	public static final PointList P_EC;

	/** 選択中 ECの描画テンプレート */
	public static final PointList P_EC_SELECTED;

	/** 非表示 ECの描画テンプレート */
	public static final PointList P_EC_HIDDEN;

	/** 自ECのスタイル */
	public static final FigureStyle S_OWN;
	/** 部分ECのスタイル */
	public static final FigureStyle S_PART;

	static {
		PointList p = new PointList(4);
		p.addPoint(4, -6);
		p.addPoint(14, -6);
		p.addPoint(14, 6);
		p.addPoint(4, 6);
		P_EC = p;

		p = new PointList(4);
		p.addPoint(0, -6);
		p.addPoint(14, -6);
		p.addPoint(14, 6);
		p.addPoint(0, 6);
		P_EC_SELECTED = p;

		p = new PointList(2);
		p.addPoint(0, -6);
		p.addPoint(0, 6);
		P_EC_HIDDEN = p;

		S_OWN = new FigureStyle(ColorConstants.darkBlue, ColorConstants.red);
		S_PART = new FigureStyle(ColorConstants.darkBlue, ColorConstants.red);
	}

	/**
	 * Owned ECの Figureを表します。
	 */
	public static class OwnECFigure extends ECFigure {

		public OwnECFigure(ECEditPart.OwnEC ec) {
			init();
			setTemplate(P_EC);

			setBackgroundColor(ECFigure.S_OWN.bg);
			setForegroundColor(ECFigure.S_OWN.fg);
		}

	}

	/**
	 * Participant ECの Figureを表します。
	 */
	public static class PartECFigure extends ECFigure {

		public PartECFigure(ECEditPart.PartEC ec) {
			init();
			setTemplate(P_EC);

			setBackgroundColor(ECFigure.S_OWN.bg);
			setForegroundColor(ECFigure.S_OWN.fg);
		}

	}

	/**
	 * 選択中の Owned ECの Figureを表します。
	 */
	public static class SelectedOwnECFigure extends OwnECFigure {

		public SelectedOwnECFigure(ECEditPart.OwnEC ec) {
			super(ec);
			setTemplate(P_EC_SELECTED);
		}

	}

	/**
	 * 選択中の Participant ECの Figureを表します。
	 */
	public static class SelectedPartECFigure extends PartECFigure {

		public SelectedPartECFigure(ECEditPart.PartEC ec) {
			super(ec);
			setTemplate(P_EC_SELECTED);
		}

	}

	/**
	 * 非表示の Owned ECの Figureを表します。
	 */
	public static class HiddenOwnECFigure extends OwnECFigure {

		public HiddenOwnECFigure(ECEditPart.OwnEC ec) {
			super(ec);
			setTemplate(P_EC_HIDDEN);
		}

	}

	/**
	 * 非表示の Participant ECの Figureを表します。
	 */
	public static class HiddenPartECFigure extends PartECFigure {

		public HiddenPartECFigure(ECEditPart.PartEC ec) {
			super(ec);
			setTemplate(P_EC_HIDDEN);
		}

	}

}