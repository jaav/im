package be.mobila.im.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 5:16:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class NameValuePair {
  private String name;
  private Object value;
  private String comment;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public boolean isValid(){
    if(value instanceof String)
      return (StringUtils.isNotBlank(name) && StringUtils.isNotBlank((String)value));
    else
      return (StringUtils.isNotBlank(name) && value!=null); 
  }
}
