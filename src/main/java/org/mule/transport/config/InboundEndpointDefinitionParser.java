package org.mule.transport.config;

import org.apache.commons.lang.StringUtils;
import org.mule.config.spring.MuleHierarchicalBeanDefinitionParserDelegate;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.transport.sources.InboundEndpointMessageSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class InboundEndpointDefinitionParser extends ChildDefinitionParser {

    public InboundEndpointDefinitionParser() {
        super("messageSource", InboundEndpointMessageSource.class);
    }

    public BeanDefinition parseChild(Element element, ParserContext parserContent) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(InboundEndpointMessageSource.class.getName());
        String configRef = element.getAttribute("config-ref");
        if ((configRef != null) && (!StringUtils.isBlank(configRef))) {
            builder.addPropertyValue("moduleObject", configRef);
        }
        if (element.hasAttribute("exchange-pattern")) {
            builder.addPropertyValue("exchangePattern", element.getAttribute("exchange-pattern"));
        }
        if (element.hasAttribute("socket-operation")) {
            builder.addPropertyValue("socketOperation", element.getAttribute("socket-operation"));
        }
        if ((element.getAttribute("address") != null) && (!StringUtils.isBlank(element.getAttribute("address")))) {
            builder.addPropertyValue("address", element.getAttribute("address"));
        }
        if ((element.getAttribute("filter") != null) && (!StringUtils.isBlank(element.getAttribute("filter")))) {
            builder.addPropertyValue("filter", element.getAttribute("filter"));
        }
        if ((element.getAttribute("multipart") != null) && (!StringUtils.isBlank(element.getAttribute("multipart")))) {
            builder.addPropertyValue("multipart", element.getAttribute("multipart"));
        }
        if ((element.getAttribute("retryMax") != null) && (!StringUtils.isBlank(element.getAttribute("retryMax")))) {
            builder.addPropertyValue("retryMax", element.getAttribute("retryMax"));
        }

        BeanDefinition definition = builder.getBeanDefinition();
        definition.setAttribute(MuleHierarchicalBeanDefinitionParserDelegate.MULE_NO_RECURSE, Boolean.TRUE);
        MutablePropertyValues propertyValues = parserContent.getContainingBeanDefinition().getPropertyValues();

        propertyValues.addPropertyValue("messageSource", definition);

        return definition;
    }

}
