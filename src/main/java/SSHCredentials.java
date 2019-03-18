public class SSHCredentials {
    private String userName;
    private String machineAddress;
    private String password;


    public SSHCredentials(String userName, String machineAddress, String password) {
        this.userName = userName;
        this.machineAddress = machineAddress;
        this.password = password;
    }

    public SSHCredentials(String userName, String machineAddress) {
        this(userName, machineAddress, null);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
