package com.danielearmellini.linebreaker;

import java.util.Comparator;

public interface BreakRule<T> extends BreakAction<T>, Comparator<T> {

}
