package ch.alpine.java.util;

public class Akten {
  public static void main(String[] args) {
    // INPUT:
    int N_akten = 100;
    double[] fte = { 1, 1, 0.3, 0.5 };
    // ALGORITHMUS:
    int M_anzahl = fte.length;
    double fte_total = 0;
    for (int index = 0; index < M_anzahl; ++index)
      fte_total += fte[index];
    // ---
    double[] m_akten_exact = new double[M_anzahl];
    for (int index = 0; index < fte.length; ++index)
      m_akten_exact[index] = N_akten * fte[index] / fte_total;
    // ---
    int[] m_akten = new int[M_anzahl];
    for (int index = 0; index < fte.length; ++index)
      m_akten[index] = (int) Math.floor(m_akten_exact[index]);
    // ---
    // abbruch kriterium: summe der tatsaechlich vergebenen akten ist N_akten
    int ueberschuss_akten = N_akten;
    for (int index = 0; index < fte.length; ++index)
      ueberschuss_akten -= m_akten[index];
    System.out.println("Akten noch zu verteilen = " + ueberschuss_akten);
    // ---
    for (int count = 0; count < ueberschuss_akten; ++count) {
      // finde Mitarbeiter mit groesster Abweichung vom _ungerundeten_ akten wert
      double m_max = Double.NEGATIVE_INFINITY; // hier kannst Du auch einfach -1 nehmen
      int m_ind = -1;
      for (int index = 0; index < fte.length; ++index) {
        double delta = m_akten_exact[index] - m_akten[index];
        if (m_max < delta) {
          m_max = delta;
          m_ind = index;
        }
      }
      System.out.println("Auswahl von Mitarbeiter " + m_ind + " wegen Abweichung=" + m_max);
      m_akten[m_ind] += 1;
    }
    System.out.println("Ergebnis:");
    for (int index = 0; index < fte.length; ++index) {
      System.out.println("Akten fuer Mitarbeiter " + index + ": " + m_akten[index]);
    }
  }
}
