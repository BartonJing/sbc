package com.barton.sbc.utils;

import java.util.List;

public interface InheritedNode<T> {
    boolean isChildFrom(T node);
    boolean isBrother(T node);
    void addChildNode(T node);
    List<T> getChildNodes();
}
