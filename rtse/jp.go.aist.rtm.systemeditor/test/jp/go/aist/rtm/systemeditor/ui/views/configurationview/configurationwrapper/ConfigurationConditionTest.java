package jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper;

import junit.framework.TestCase;

public class ConfigurationConditionTest extends TestCase {

	public void testParse() throws Exception {
		ConfigurationCondition actual = ConfigurationCondition.parse("");
		assertEquals(true, actual.isNull());

		// ���l(100)
		actual = ConfigurationCondition.parse("100");
		assertEquals(true, actual.hasConstValue());
		assertEquals("100", actual.getConstValue());
		// ���l(10a) �G���[
		try {
			actual = ConfigurationCondition.parse("10a");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}

		// (ichi,one),(ni,two),(san,three) (�擪���񋓂̔z��)
		actual = ConfigurationCondition.parse("(ichi,one),(ni,two),(san,three)");
		assertTrue(actual.isArrayCondition());
		assertEquals(3, actual.getArraySize());
		assertEquals(true, actual.getCondition(0).hasEnumList());
		assertEquals("ichi", actual.getCondition(0).getEnumList().get(0));
		assertEquals("one", actual.getCondition(0).getEnumList().get(1));
		assertEquals(true, actual.getCondition(1).hasEnumList());
		assertEquals("ni", actual.getCondition(1).getEnumList().get(0));
		assertEquals("two", actual.getCondition(1).getEnumList().get(1));
		assertEquals(true, actual.getCondition(2).hasEnumList());
		assertEquals("san", actual.getCondition(2).getEnumList().get(0));
		assertEquals("three", actual.getCondition(2).getEnumList().get(1));

		// (ichi,one) ,(ni,two), (san,three) (�擪���񋓂̔z��)
		actual = ConfigurationCondition.parse("(ichi,one),(ni,two),(san,three)");
		assertTrue(actual.isArrayCondition());
		assertEquals(3, actual.getArraySize());
		assertEquals(true, actual.getCondition(0).hasEnumList());
		assertEquals("ichi", actual.getCondition(0).getEnumList().get(0));
		assertEquals("one", actual.getCondition(0).getEnumList().get(1));
		assertEquals(true, actual.getCondition(1).hasEnumList());
		assertEquals("ni", actual.getCondition(1).getEnumList().get(0));
		assertEquals("two", actual.getCondition(1).getEnumList().get(1));
		assertEquals(true, actual.getCondition(2).hasEnumList());
		assertEquals("san", actual.getCondition(2).getEnumList().get(0));
		assertEquals("three", actual.getCondition(2).getEnumList().get(1));
	}

	public void testParseXError() throws Exception {
		// x�Ȃ� (���l�G���[)
		try {
			ConfigurationCondition.parseX("0<");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
		// �s�����Ȃ�
		try {
			ConfigurationCondition.parseX("0=x");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
		try {
			ConfigurationCondition.parseX("x==1");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
	}

	public void testParseXMax() throws Exception {
		ConfigurationCondition actual;
		// x<1.0
		actual = ConfigurationCondition.parseX("x<1.0");
		assertEquals("1.0", actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals(null, actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// x<=100
		actual = ConfigurationCondition.parseX("x<=100");
		assertEquals("100", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals(null, actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// x<=-5.0
		actual = ConfigurationCondition.parseX("x<=-5.0");
		assertEquals("-5.0", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals(null, actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// x<10a �ő�l�t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("x<10a");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}

		// 1.0>x
		actual = ConfigurationCondition.parseX("1.0>x");
		assertEquals("1.0", actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals(null, actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// 100>=x
		actual = ConfigurationCondition.parseX("100>=x");
		assertEquals("100", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals(null, actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// -5.0>=x
		actual = ConfigurationCondition.parseX("-5.0>=x");
		assertEquals("-5.0", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals(null, actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// 10a>x �ő�l�t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("10a>x");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
	}

	public void testParseXMin() throws Exception {
		ConfigurationCondition actual;
		// x>1.0
		actual = ConfigurationCondition.parseX("x>1.0");
		assertEquals(null, actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("1.0", actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// x>=100
		actual = ConfigurationCondition.parseX("x>=100");
		assertEquals(null, actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("100", actual.getMin());
		assertEquals(true, actual.isMinEquals());
		// x>=-5.0
		actual = ConfigurationCondition.parseX("x>=-5.0");
		assertEquals(null, actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("-5.0", actual.getMin());
		assertEquals(true, actual.isMinEquals());
		// x>10a �ŏ��l�t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("x>10a");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
		// 1.0<x
		actual = ConfigurationCondition.parseX("1.0<x");
		assertEquals(null, actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("1.0", actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// 100<=x
		actual = ConfigurationCondition.parseX("100<=x");
		assertEquals(null, actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("100", actual.getMin());
		assertEquals(true, actual.isMinEquals());
		// -5.0<=x
		actual = ConfigurationCondition.parseX("-5.0<=x");
		assertEquals(null, actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("-5.0", actual.getMin());
		assertEquals(true, actual.isMinEquals());
		// 10a<x �ŏ��l�t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("10a<x");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
	}

	public void testParseXMinMax() throws Exception {
		ConfigurationCondition actual;
		// 0<x<100
		actual = ConfigurationCondition.parseX("0<x<100");
		assertEquals("100", actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("0", actual.getMin());
		assertEquals(false, actual.isMinEquals());
		assertFalse(actual.validate("0"));
		assertTrue(actual.validate("1"));
		assertFalse(actual.validate("100"));
		// 0.0<x<100.0
		actual = ConfigurationCondition.parseX("0.0<x<100.0");
		assertEquals("100.0", actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("0.0", actual.getMin());
		assertEquals(false, actual.isMinEquals());
		assertFalse(actual.validate("0"));
		assertTrue(actual.validate("1"));
		assertFalse(actual.validate("100"));
		// 0.1<=x<=0.9
		actual = ConfigurationCondition.parseX("0.1<=x<=0.9");
		assertEquals("0.9", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals("0.1", actual.getMin());
		assertEquals(true, actual.isMinEquals());
		assertFalse(actual.validate("0"));
		assertTrue(actual.validate("0.1"));
		assertTrue(actual.validate("0.5"));
		assertTrue(actual.validate("0.9"));
		assertFalse(actual.validate("1"));
		// -0.5<x<=0.5
		actual = ConfigurationCondition.parseX("-0.5<x<=0.5");
		assertEquals("0.5", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals("-0.5", actual.getMin());
		assertEquals(false, actual.isMinEquals());

		// 100>x>0
		actual = ConfigurationCondition.parseX("100>x>0");
		assertEquals("100", actual.getMax());
		assertEquals(false, actual.isMaxEquals());
		assertEquals("0", actual.getMin());
		assertEquals(false, actual.isMinEquals());
		// 0.9>=x>=0.1
		actual = ConfigurationCondition.parseX("0.9>=x>=0.1");
		assertEquals("0.9", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals("0.1", actual.getMin());
		assertEquals(true, actual.isMinEquals());
		// 0.5>=x>-0.5
		actual = ConfigurationCondition.parseX("0.5>=x>-0.5");
		assertEquals("0.5", actual.getMax());
		assertEquals(true, actual.isMaxEquals());
		assertEquals("-0.5", actual.getMin());
		assertEquals(false, actual.isMinEquals());

		// -10<x<10a �ő�l�t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("-10<x<10a");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
		// -10a<=x<10 �ŏ��l�t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("-10a<=x<10");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
		// -10<x<= �ő�l�Ȃ��t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("-10<x<=");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}
		// <=x<=10 �ŏ��l�Ȃ��t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("<x<=10");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}

		// 1<x>1 �s�����̌������s��v
		try {
			actual = ConfigurationCondition.parseX("1<x>1");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
		// 10>=x<=10 �s�����̌������s��v
		try {
			actual = ConfigurationCondition.parseX("10>=x<=10");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}

		// 10<x<1 �ŏ��l�A�ő�l�̑召�G���[
		try {
			actual = ConfigurationCondition.parseX("10<x<1");
			fail();
		} catch (ConfigurationCondition.NumberException e) {
			assertTrue(true);
		}

		// <x<1 �����Ȃ��t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("<x<1");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}

		// 1<x< �E���Ȃ��t�H�[�}�b�g�G���[
		try {
			actual = ConfigurationCondition.parseX("1<x<");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
	}

	public void testParseEnum() throws Exception {
		ConfigurationCondition actual;
		// (100,200,300)
		actual = ConfigurationCondition.parseEnum("(100,200,300)");
		assertEquals(true, actual.hasEnumList());
		assertEquals(3, actual.getEnumList().size());
		assertEquals("100", actual.getEnumList().get(0));
		assertEquals("200", actual.getEnumList().get(1));
		assertEquals("300", actual.getEnumList().get(2));
		// (100, �����ʂȂ�
		try {
			actual = ConfigurationCondition.parseEnum("(100,");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
		// 100,200) �J�����ʂȂ�
		try {
			actual = ConfigurationCondition.parseEnum("100,200)");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
	}

	public void testParseArray() throws Exception {
		ConfigurationCondition actual;
		// 0<x<1, 1<=x<=2, 2<x
		actual = ConfigurationCondition.parseArray("0<x<1, 1<=x<=2, 2<x");
		assertTrue(actual.isArrayCondition());
		assertEquals(3, actual.getArraySize());
		assertEquals("0", actual.getCondition(0).getMin());
		assertEquals(false, actual.getCondition(0).isMinEquals());
		assertEquals("1", actual.getCondition(0).getMax());
		assertEquals(false, actual.getCondition(0).isMaxEquals());
		assertEquals("1", actual.getCondition(1).getMin());
		assertEquals(true, actual.getCondition(1).isMinEquals());
		assertEquals("2", actual.getCondition(1).getMax());
		assertEquals(true, actual.getCondition(1).isMaxEquals());
		assertEquals("2", actual.getCondition(2).getMin());
		assertEquals(false, actual.getCondition(2).isMinEquals());
		assertEquals(null, actual.getCondition(2).getMax());
		assertEquals(false, actual.getCondition(2).isMaxEquals());
		assertEquals(null, actual.getCondition(3));

		// 0.0<=x<=1.0, , (r1,r2,r3)
		actual = ConfigurationCondition.parseArray("0.0<=x<=1.0, , (r1,r2,r3)");
		assertTrue(actual.isArrayCondition());
		assertEquals(3, actual.getArraySize());
		assertEquals("0.0", actual.getCondition(0).getMin());
		assertEquals(true, actual.getCondition(0).isMinEquals());
		assertEquals("1.0", actual.getCondition(0).getMax());
		assertEquals(true, actual.getCondition(0).isMaxEquals());
		assertEquals(true, actual.getCondition(1).validate("")); // �����
		assertEquals(null, actual.getCondition(1).getMin());
		assertEquals(null, actual.getCondition(1).getMax());
		assertEquals(false, actual.getCondition(1).hasEnumList());
		assertEquals(true, actual.getCondition(2).hasEnumList());
		assertEquals(3, actual.getCondition(2).getEnumList().size());

		// 0.0>=x, (r1,r2,r3) ,
		actual = ConfigurationCondition.parseArray("0.0>=x, (r1,r2,r3) ,");
		assertTrue(actual.isArrayCondition());
		assertEquals(3, actual.getArraySize());
		assertEquals(null, actual.getCondition(0).getMin());
		assertEquals(false, actual.getCondition(0).isMinEquals());
		assertEquals("0.0", actual.getCondition(0).getMax());
		assertEquals(true, actual.getCondition(0).isMaxEquals());
		assertEquals(true, actual.getCondition(1).hasEnumList());
		assertEquals(3, actual.getCondition(1).getEnumList().size());
		assertEquals(true, actual.getCondition(2).validate("")); // �����
		assertEquals(null, actual.getCondition(2).getMin());
		assertEquals(null, actual.getCondition(2).getMax());
		assertEquals(false, actual.getCondition(2).hasEnumList());

		// ,, , 
		actual = ConfigurationCondition.parseArray(",, , ");
		assertTrue(actual.isArrayCondition());
		assertEquals(4, actual.getArraySize());
		assertEquals(true, actual.getCondition(0).validate("")); // �����
		assertEquals(null, actual.getCondition(0).getMin());
		assertEquals(null, actual.getCondition(0).getMax());
		assertEquals(false, actual.getCondition(0).hasEnumList());
		assertEquals(true, actual.getCondition(1).validate("")); // �����
		assertEquals(null, actual.getCondition(1).getMin());
		assertEquals(null, actual.getCondition(1).getMax());
		assertEquals(false, actual.getCondition(1).hasEnumList());
		assertEquals(true, actual.getCondition(2).validate("")); // �����
		assertEquals(null, actual.getCondition(2).getMin());
		assertEquals(null, actual.getCondition(2).getMax());
		assertEquals(false, actual.getCondition(2).hasEnumList());
		assertEquals(true, actual.getCondition(3).validate("")); // �����
		assertEquals(null, actual.getCondition(3).getMin());
		assertEquals(null, actual.getCondition(3).getMax());
		assertEquals(false, actual.getCondition(3).hasEnumList());

		// 0<x<1, 1<x<, 2<x<3 (�ꕔ���G���[)
		actual = ConfigurationCondition.parseArray("0<x<1, 1<x<, 2<x<3");
		assertTrue(actual.isArrayCondition());
		assertEquals(3, actual.getArraySize());
		assertEquals("0", actual.getCondition(0).getMin());
		assertEquals("1", actual.getCondition(0).getMax());
		assertEquals(true, actual.getCondition(1).isNull());
		assertEquals("2", actual.getCondition(2).getMin());
		assertEquals("3", actual.getCondition(2).getMax());
	}

	public void testParseHash() throws Exception {
		ConfigurationCondition actual;
		// {key0: 0<x<1, key1:1<=x<=2, key2: 2<x}
		actual = ConfigurationCondition.parseHash("{key0: 0<x<1, key1:1<=x<=2, key2: 2<x}");
		assertTrue(actual.isHashCondition());
		assertEquals("0", actual.getCondition("key0").getMin());
		assertEquals(false, actual.getCondition("key0").isMinEquals());
		assertEquals("1", actual.getCondition("key0").getMax());
		assertEquals(false, actual.getCondition("key0").isMaxEquals());
		assertEquals("1", actual.getCondition("key1").getMin());
		assertEquals(true, actual.getCondition("key1").isMinEquals());
		assertEquals("2", actual.getCondition("key1").getMax());
		assertEquals(true, actual.getCondition("key1").isMaxEquals());
		assertEquals("2", actual.getCondition("key2").getMin());
		assertEquals(false, actual.getCondition("key2").isMinEquals());
		assertEquals(null, actual.getCondition("key2").getMax());
		assertEquals(false, actual.getCondition("key2").isMaxEquals());
		assertEquals(null, actual.getCondition("key3"));

		// {key0:0.0<=x<=1.0, key1:, key2:(r1,r2,r3) }
		actual = ConfigurationCondition.parseHash("{key0:0.0<=x<=1.0, key1:, key2:(r1,r2,r3) }");
		assertTrue(actual.isHashCondition());
		assertEquals("0.0", actual.getCondition("key0").getMin());
		assertEquals(true, actual.getCondition("key0").isMinEquals());
		assertEquals("1.0", actual.getCondition("key0").getMax());
		assertEquals(true, actual.getCondition("key0").isMaxEquals());
		assertEquals(true, actual.getCondition("key1").validate("")); // �����
		assertEquals(null, actual.getCondition("key1").getMin());
		assertEquals(null, actual.getCondition("key1").getMax());
		assertEquals(false, actual.getCondition("key1").hasEnumList());
		assertEquals(true, actual.getCondition("key2").hasEnumList());
		assertEquals(3, actual.getCondition("key2").getEnumList().size());

		// {key0: 0.0>=x,key1: (r1,r2,r3) ,key2:  }
		actual = ConfigurationCondition.parseHash("{key0: 0.0>=x,key1: (r1,r2,r3) ,key2:  }");
		assertTrue(actual.isHashCondition());
		assertEquals(null, actual.getCondition("key0").getMin());
		assertEquals(false, actual.getCondition("key0").isMinEquals());
		assertEquals("0.0", actual.getCondition("key0").getMax());
		assertEquals(true, actual.getCondition("key0").isMaxEquals());
		assertEquals(true, actual.getCondition("key1").hasEnumList());
		assertEquals(3, actual.getCondition("key1").getEnumList().size());
		assertEquals(true, actual.getCondition("key2").validate("")); // �����
		assertEquals(null, actual.getCondition("key2").getMin());
		assertEquals(null, actual.getCondition("key2").getMax());
		assertEquals(false, actual.getCondition("key2").hasEnumList());

		// {key0:  ,key1:, key2:, key3:}
		actual = ConfigurationCondition.parseHash("{key0:  ,key1:, key2:, key3:}");
		assertTrue(actual.isHashCondition());
		assertEquals(true, actual.getCondition("key0").validate("")); // �����
		assertEquals(null, actual.getCondition("key0").getMin());
		assertEquals(null, actual.getCondition("key0").getMax());
		assertEquals(false, actual.getCondition("key0").hasEnumList());
		assertEquals(true, actual.getCondition("key1").validate("")); // �����
		assertEquals(null, actual.getCondition("key1").getMin());
		assertEquals(null, actual.getCondition("key1").getMax());
		assertEquals(false, actual.getCondition("key1").hasEnumList());
		assertEquals(true, actual.getCondition("key2").validate("")); // �����
		assertEquals(null, actual.getCondition("key2").getMin());
		assertEquals(null, actual.getCondition("key2").getMax());
		assertEquals(false, actual.getCondition("key2").hasEnumList());
		assertEquals(true, actual.getCondition("key3").validate("")); // �����
		assertEquals(null, actual.getCondition("key3").getMin());
		assertEquals(null, actual.getCondition("key3").getMax());
		assertEquals(false, actual.getCondition("key3").hasEnumList());

		// {key0: 0<x<1, key1:1<x<, key2:2<x<3} (�ꕔ���G���[)
		actual = ConfigurationCondition.parseHash("{key0: 0<x<1, key1:1<x<, key2:2<x<3}");
		assertTrue(actual.isHashCondition());
		assertEquals(3, actual.getHashKeySet().size());
		assertEquals("0", actual.getCondition("key0").getMin());
		assertEquals("1", actual.getCondition("key0").getMax());
		assertEquals(true, actual.getCondition("key1").isNull());
		assertEquals("2", actual.getCondition("key2").getMin());
		assertEquals("3", actual.getCondition("key2").getMax());

		// {key0: 0<x<1 �����ʂȂ�
		try {
			actual = ConfigurationCondition.parseHash("{key0: 0<x<1");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
		// { : 0<x<1} �L�[�Ȃ�
		try {
			actual = ConfigurationCondition.parseHash("{ : 0<x<1}");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
		// { key0: 0<x,:2>x } �L�[�Ȃ�
		try {
			actual = ConfigurationCondition.parseHash("{ key0: 0<x,:2>x }");
			fail();
		} catch (ConfigurationCondition.FormatException e) {
			assertTrue(true);
		}
	}

	public void testGetDigits() throws Exception {
		ConfigurationCondition actual;
		// ����
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEquals(0, actual.getDigits());
		// ����
		actual = ConfigurationCondition.parseX("-10.0<x<10");
		assertEquals(1, actual.getDigits());
		actual = ConfigurationCondition.parseX("-10<x<10.00");
		assertEquals(2, actual.getDigits());
	}

	public void testGetByInteger() throws Exception {
		ConfigurationCondition actual;
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEquals(10, actual.getMaxByInteger().intValue());
		assertEquals(-10, actual.getMinByInteger().intValue());
		actual = ConfigurationCondition.parseX("-10.0<x<10.00");
		assertEquals(1000, actual.getMaxByInteger().intValue());
		assertEquals(-1000, actual.getMinByInteger().intValue());
	}

	public void testGetIntegerByDigits() throws Exception {
		ConfigurationCondition actual;
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEquals(10, actual.getIntegerByDigits(10.0).intValue());
		actual = ConfigurationCondition.parseX("-10.0<x<10.00");
		assertEquals(1000, actual.getIntegerByDigits(10.0).intValue());
	}

	public void testGetDecimalByDigits() throws Exception {
		ConfigurationCondition actual;
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEquals(10.0, actual.getDecimalByDigits(10));
		actual = ConfigurationCondition.parseX("-10.0<x<10.00");
		assertEquals(100.0, actual.getDecimalByDigits(10000));
	}

	public void testGetStepByValue() throws Exception {
		ConfigurationCondition actual;
		// ����
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEquals(9, actual.getStepByValue("-1", 20));
		assertEquals(0, actual.getStepByValue("-11", 20)); // �͈͊O
		// ����
		actual = ConfigurationCondition.parseX("-0.5<x<0.5");
		assertEquals(4, actual.getStepByValue("-0.3", 20));
		assertEquals(0, actual.getStepByValue("0.6", 20)); // �͈͊O
		actual = ConfigurationCondition.parseX("-10.0<x<10.0");
		assertEquals(20, actual.getStepByValue("9.9", 20));
		assertEquals(20, actual.getStepByValue("10.0", 20));
	}

	public void testGetValueByStep() throws Exception {
		ConfigurationCondition actual;
		// ����
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEquals("-1", actual.getValueByStep(9, 20));
		assertEquals("0", actual.getValueByStep(-1, 20)); // �͈͊O
		assertEquals("0", actual.getValueByStep(21, 20)); // �͈͊O
		// ����
		actual = ConfigurationCondition.parseX("-0.5<x<0.5");
		assertEquals("-0.3", actual.getValueByStep(4, 20));
		assertEquals("0", actual.getValueByStep(-1, 20)); // �͈͊O
		assertEquals("0", actual.getValueByStep(21, 20)); // �͈͊O
		actual = ConfigurationCondition.parseX("-10.0<x<10.0");
		assertEquals("9.0", actual.getValueByStep(19, 20));
	}

	public void testGetValueByStep2() throws Exception {
		ConfigurationCondition condition = ConfigurationCondition.parseX("0.0<x<10.0");
		assertEquals("0.0", condition.getValueByStep(0, 100));
		assertEquals("0.1", condition.getValueByStep(1, 100));
		assertEquals("0.2", condition.getValueByStep(2, 100));
		assertEquals("0.3", condition.getValueByStep(3, 100));
		assertEquals(3, condition.getStepByValue("0.3", 100));
		assertEquals("0.4", condition.getValueByStep(4, 100));
		assertEquals(4, condition.getStepByValue("0.4", 100));
		assertEquals("0.7", condition.getValueByStep(7, 100));
	}
	public void testValidate() throws Exception {
		ConfigurationCondition actual;
		// ���l
		actual = ConfigurationCondition.parse("10.0");
		assertEquals(true, actual.validate("10.0"));
		assertEquals(true, actual.validate("10"));
		assertEquals(false, actual.validate("11.0"));
		// �񋓌^
		actual = ConfigurationCondition.parse("(100,200,aaa)");
		assertEquals(true, actual.validate("100"));
		assertEquals(true, actual.validate("200"));
		assertEquals(true, actual.validate("aaa"));
		assertEquals(false, actual.validate("100.0"));
		assertEquals(false, actual.validate("aab"));
		// �����̂�(x>)
		actual = ConfigurationCondition.parse("x>10.0");
		assertEquals(false, actual.validate("9.9"));
		assertEquals(false, actual.validate("10.0"));
		assertEquals(true, actual.validate("10.1"));
		// �����̂�(x>=)
		actual = ConfigurationCondition.parse("x>=10.0");
		assertEquals(false, actual.validate("9.9"));
		assertEquals(true, actual.validate("10.0"));
		assertEquals(true, actual.validate("10.1"));
		// ����̂�(x<)
		actual = ConfigurationCondition.parse("x<10.0");
		assertEquals(true, actual.validate("9.9"));
		assertEquals(false, actual.validate("10.0"));
		assertEquals(false, actual.validate("10.1"));
		// ����̂�(x<=)
		actual = ConfigurationCondition.parse("x<=10.0");
		assertEquals(true, actual.validate("9.9"));
		assertEquals(true, actual.validate("10.0"));
		assertEquals(false, actual.validate("10.1"));
		// �͈�(-10<x<10)
		actual = ConfigurationCondition.parse("-10<x<10");
		assertEquals(false, actual.validate("-10.1"));
		assertEquals(false, actual.validate("-10.0"));
		assertEquals(true, actual.validate("-9.9"));
		assertEquals(true, actual.validate("9.9"));
		assertEquals(false, actual.validate("10.0"));
		assertEquals(false, actual.validate("10.1"));
		// �͈�(-10<=x<10)
		actual = ConfigurationCondition.parse("-10<=x<10");
		assertEquals(false, actual.validate("-10.1"));
		assertEquals(true, actual.validate("-10.0"));
		assertEquals(true, actual.validate("-9.9"));
		assertEquals(true, actual.validate("9.9"));
		assertEquals(false, actual.validate("10.0"));
		assertEquals(false, actual.validate("10.1"));
		// �͈�(-10<x<=10)
		actual = ConfigurationCondition.parse("-10<x<=10");
		assertEquals(false, actual.validate("-10.1"));
		assertEquals(false, actual.validate("-10.0"));
		assertEquals(true, actual.validate("-9.9"));
		assertEquals(true, actual.validate("9.9"));
		assertEquals(true, actual.validate("10.0"));
		assertEquals(false, actual.validate("10.1"));
		// �͈�(-10<=x<=10)
		actual = ConfigurationCondition.parse("-10<=x<=10");
		assertEquals(false, actual.validate("-10.1"));
		assertEquals(true, actual.validate("-10.0"));
		assertEquals(true, actual.validate("-9.9"));
		assertEquals(true, actual.validate("9.9"));
		assertEquals(true, actual.validate("10.0"));
		assertEquals(false, actual.validate("10.1"));
	}

	public void testClone() throws Exception {
		ConfigurationCondition actual;
		// ���l
		actual = ConfigurationCondition.parse("10.0");
		assertEqualsClone(actual, actual.clone());
		// �͈͎w��
		actual = ConfigurationCondition.parseX("-10<x<10");
		assertEqualsClone(actual, actual.clone());
		// �񋓌^
		actual = ConfigurationCondition.parseEnum("(100,200,300)");
		assertEqualsClone(actual, actual.clone());
		// �z��^
		actual = ConfigurationCondition.parseArray("100, 0<=x<1,(100,200,300)");
		assertEqualsClone(actual, actual.clone());
		// �n�b�V���^
		actual = ConfigurationCondition.parseHash("{key0:100, key1: 0<=x<1,key2: (100,200,300) }");
		assertEqualsClone(actual, actual.clone());
	}

	public void assertEqualsClone(ConfigurationCondition expected,
			ConfigurationCondition actual) {
		assertEquals(expected.getConstValue(), actual.getConstValue());
		assertEquals(expected.getMax(), actual.getMax());
		assertEquals(expected.getMin(), actual.getMin());
		assertEquals(expected.isMaxEquals(), actual.isMaxEquals());
		assertEquals(expected.isMinEquals(), actual.isMinEquals());
		assertEquals(expected.getDigits(), actual.getDigits());
		if (expected.getEnumList() != null) {
			assertEquals(expected.getEnumList().size(), actual.getEnumList()
					.size());
			for (int i = 0; i < expected.getEnumList().size(); i++) {
				assertEquals(expected.getEnumList().get(i), actual
						.getEnumList().get(i));
			}
		}
		if (expected.isArrayCondition()) {
			assertTrue(actual.isArrayCondition());
			assertEquals(expected.getArraySize(), actual.getArraySize());
			for (int i = 0; i < expected.getArraySize(); i++) {
				this.assertEqualsClone(expected.getCondition(i), actual
						.getCondition(i));
			}
		}
		if (expected.isHashCondition()) {
			assertTrue(actual.isHashCondition());
			for (String key : expected.getHashKeySet()) {
				this.assertEqualsClone(expected.getCondition(key), actual
						.getCondition(key));
			}
		}
	}
}