module myModule {
    // https://stackoverflow.com/questions/73590370/javafx-sslhandshakeexception-received-fatal-alert-handshake-failure
    requires jdk.crypto.ec;
    requires javafx.controls;
    requires java.xml;
//    requires com.rometools.rome;

    exports com.example.app;
}