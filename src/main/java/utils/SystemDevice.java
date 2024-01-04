package utils;

public class SystemDevice {
    protected String brokerString;
    protected String clientId;
    protected String username;
    protected String password;

    public SystemDevice() {};

    public SystemDevice(String brokerString, String clientId, String username, String password) {
        this.brokerString = brokerString;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }
}
