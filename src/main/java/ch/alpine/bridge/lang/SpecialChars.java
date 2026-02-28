// code by jph
package ch.alpine.bridge.lang;

public enum SpecialChars {
  DIGIT_1("1️⃣"),
  DIGIT_2("2️⃣"),
  DIGIT_3("3️⃣"),
  DIGIT_4("4️⃣"),
  DIGIT_5("5️⃣"),
  DIGIT_6("6️⃣"),
  DIGIT_7("7️⃣"),
  DIGIT_8("8️⃣"),
  UNIVERSE("🌌"),
  OWLETS("🦉"),
  LAPTOP("💻"),
  BLAST("💥"),
  WINDOW("🪟"),
  PENCIL("✏️"),
  MAGNIFY("🔎"),
  MICROSCOPE("🔬"),
  BUILD("⚙️"),
  BULLET_POINT("🔴"),
  DIAMOND("🔷"),
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
  WHITE_MEDIUM_STAR("⭐"),
  STAR_SPARKLE("🌟"),
  TROPHY("🏆"),
  BRAIN("🧠"),
  WATCH("👀"),
  HAND("✋"),
  HAND_WAVE("👋"),
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
