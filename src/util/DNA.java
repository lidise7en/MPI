package util;

import java.util.Arrays;

import Interface.KMNum;
import constant.Constants.DNA_ELEMENT;

public class DNA implements KMNum {

    private static final long serialVersionUID = 1L;
    private DNA_ELEMENT[] element = null;

    public DNA(DNA_ELEMENT[] ele) {
        this.element = ele;
    }

    public DNA_ELEMENT[] getElement() {
        return element;
    }

    public void setElement(DNA_ELEMENT[] element) {
        this.element = element;
    }

    @Override
    public double CalDistance(KMNum e) {
        DNA_ELEMENT[] dnaString = ((DNA) e).getElement();
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
            // same length
            if (p.element.length != this.element.length) {
                return false;
            }
            
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
