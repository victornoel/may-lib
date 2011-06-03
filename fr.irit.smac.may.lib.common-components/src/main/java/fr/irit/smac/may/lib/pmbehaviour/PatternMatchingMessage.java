package fr.irit.smac.may.lib.pmbehaviour;

import java.io.Serializable;

public class PatternMatchingMessage implements Serializable {

  private static final long serialVersionUID = 3355952182125630007L;

  final Object[] content;

  /**
   * Extend this class to define new messages that can be pattern matched.
   * 
   * Call the super constructor to define the content of the message.
   * Their types will drive the matching with {@link PatternMatchingBehavior}.
   * @param params
   */
  public PatternMatchingMessage(Object... params) {
    content = params;
  }
  
  /*
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "(");
    for(Object o: content) {
      sb.append(o.getClass().getSimpleName() + ": \"" + o.toString() + "\",");
    }
    sb.delete(sb.length()-1, sb.length()+1);
    sb.append(")");
    return sb.toString();
  }
  */
}
