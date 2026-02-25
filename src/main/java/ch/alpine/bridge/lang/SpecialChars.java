// code by jph
package ch.alpine.bridge.lang;

public enum SpecialChars {
  DIGIT_1("1️⃣"),
  DIGIT_2("2️⃣"),
  DIGIT_3("3️⃣"),
  DIGIT_4("4️⃣"),
  OWLETS("🦉"),
  LAPTOP("💻"),
  BLAST("💥"),
  MAGNIFY("🔎"),
  MICROSCOPE("🔬"),
  BULLET_POINT("🔴"),
  LAB_VILE("🧪"),
  BULB("💡"),
  GOAL_FLAG("🏁"),
  KEYBOARD("⌨"),
  MOUSE("\uD83D\uDDB1"),
  DESKTOP_COMPUTER("\uD83D\uDDA5"),
  WHITE_HEAVY_CHECK_MARK("✅"),
  CROSS_MARK("❌"),
  FOLDER("📂"),
  SPARKLES("✨"),
  TROPHY("🏆"),
  WHITE_MEDIUM_STAR("⭐"),
  BRAIN("🧠"),
  THUMBS_UP_SIGN("👍"),
  SOME("👉"),
  CRISTAL("🔮"),
  PALETTE("🎨"),
  MEDAL_1("🥇"),
  MEDAL_2("🥈"),
  MEDAL_3("🥉"),
  WARNING("⚠️"),
  FIRE("🔥"),
  DNA("🧬"),
  TARGET("🎯"),
  POLICE("🚨"),
  ROCKET("🚀"),
  //
  ;

  private final String string;

  SpecialChars(String string) {
    this.string = string;
  }

  public String string() {
    return string;
  }
}
