package be.mobila.im.flow.communicate.receivers;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 13, 2010
 * Time: 2:24:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailProps {
  private String host;
  private String username;
  private String password;
  private String fromName;
  private String fromAddress;
  private String protocol;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFromName() {
    return fromName;
  }

  public void setFromName(String fromName) {
    this.fromName = fromName;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }
}
