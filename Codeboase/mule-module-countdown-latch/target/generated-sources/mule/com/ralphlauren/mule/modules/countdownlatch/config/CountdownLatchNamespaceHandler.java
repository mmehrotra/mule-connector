
package com.ralphlauren.mule.modules.countdownlatch.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/countdown-latch</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.7.1", date = "2017-01-30T02:54:21+05:30", comments = "Build UNNAMED.2613.77421cc")
public class CountdownLatchNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(CountdownLatchNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [countdown-latch] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [countdown-latch] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new MuleModuleCountdownLatchConnectorConnectorConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("create", new CreateDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("create", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("reset", new ResetDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("reset", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("count-down", new CountDownDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("count-down", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("await", new AwaitDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("await", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-available-counts", new GetAvailableCountsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-available-counts", "@Processor", ex);
        }
    }

}
