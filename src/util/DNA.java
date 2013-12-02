package util;

import java.util.Arrays;

import Interface.KMNum;

public class DNA implements KMNum {

    private static final long serialVersionUID = 1L;
    private String[] element = null;

    public DNA(String[] ele) {
        this.element = ele;
    }

    public String[] getElement() {
        return element;
    }

    public void setElement(String[] element) {
        this.element = element;
    }

    @Override
    public double CalDistance(KMNum e) {
        String[] dnaString = ((DNA) e).getElement();
        if (element.length != dnaString.length)
            System.out.println("Two DNAs' length are not the same");
        double result = 0;
        for (int i = 0; i < element.length; i++) {
            if (!element[i].equals(dnaString[i])) {
                result++;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "DNA [element=" + Arrays.toString(element) + "]";
    }

    public KMNum clone() {
        return new DNA(this.element);
    }

    @Override
    public boolean equals(Object pair) {
        if (pair instanceof DNA) {
            DNA p = (DNA) pair;
            for (int i = 0; i < element.length; i++) {
                if (!p.element[i].equals(this.element[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(element);
    }
}
