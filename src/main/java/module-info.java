module no.ssb.lds.gsim.okhttp {
    requires com.fasterxml.jackson.databind;
    requires okhttp3;
    requires static ch.qos.logback.classic;
    requires org.slf4j;
    requires com.fasterxml.jackson.annotation;

    exports no.ssb.lds.gsim.okhttp;
}