package org.vinalynn.voicectrl.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

/**
 * User: pecho
 * Date: 4/13/13
 * Time: 7:49 PM
 * E_Mail:rudys.eva@gmail.com
 *
 * @author Qinjin Peng
 * @version 1.0
 */
public class AppContextFactory {
    private static ApplicationContext ac;

    private static Boolean isInit = false;

    /**
     * Return the unique id of this application context.
     * @return the unique id of the context, or {@code null} if none
     */
    public static String getId() {
        return ac.getId();
    }

    /**
     * Return the timestamp when this context was first loaded.
     * @return the timestamp (ms) when this context was first loaded
     */
    public static long getStartupDate() {
        return ac.getStartupDate();
    }

    /**
     * Return the Environment for this object
     */
    public static Environment getEnvironment() {
        return ac.getEnvironment();
    }

    /**
     * Check whether the bean with the given name matches the specified type.
     * More specifically, check whether a {@link #getBean} call for the given name
     * would return an object that is assignable to the specified target type.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to query
     * @param targetType the type to match against
     * @return {@code true} if the bean type matches,
     * {@code false} if it doesn't match or cannot be determined yet
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no bean with the given name
     * @since 2.0.1
     * @see #getBean
     * @see #getType
     */
    public static boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
        return ac.isTypeMatch(name, targetType);
    }

    /**
     * Expose AutowireCapableBeanFactory functionality for this context.
     * <p>This is not typically used by application code, except for the purpose
     * of initializing bean instances that live outside the application context,
     * applying the Spring bean lifecycle (fully or partly) to them.
     * <p>Alternatively, the internal BeanFactory exposed by the
     * {@link org.springframework.context.ConfigurableApplicationContext} interface offers access to the
     * AutowireCapableBeanFactory interface too. The present method mainly
     * serves as convenient, specific facility on the ApplicationContext
     * interface itself.
     * @return the AutowireCapableBeanFactory for this context
     * @throws IllegalStateException if the context does not support
     * the AutowireCapableBeanFactory interface or does not hold an autowire-capable
     * bean factory yet (usually if {@code refresh()} has never been called)
     * @see org.springframework.context.ConfigurableApplicationContext#refresh()
     * @see org.springframework.context.ConfigurableApplicationContext#getBeanFactory()
     */
    public static AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return ac.getAutowireCapableBeanFactory();
    }

    /**
     * Determine the type of the bean with the given name. More specifically,
     * determine the type of object that {@link #getBean} would return for the given name.
     * <p>For a {@link org.springframework.beans.factory.FactoryBean}, return the type of object that the FactoryBean creates,
     * as exposed by {@link org.springframework.beans.factory.FactoryBean#getObjectType()}.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to query
     * @return the type of the bean, or {@code null} if not determinable
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no bean with the given name
     * @since 1.1.2
     * @see #getBean
     * @see #isTypeMatch
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return ac.getType(name);
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Behaves the same as {@link #getBean(String)}, but provides a measure of type
     * safety by throwing a BeanNotOfRequiredTypeException if the bean is not of the
     * required type. This means that ClassCastException can't be thrown on casting
     * the result correctly, as can happen with {@link #getBean(String)}.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to retrieve
     * @param requiredType type the bean must match. Can be an interface or superclass
     * of the actual class, or {@code null} for any match. For example, if the value
     * is {@code Object.class}, this method will succeed whatever the class of the
     * returned instance.
     * @return an instance of the bean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no such bean definition
     * @throws org.springframework.beans.factory.BeanNotOfRequiredTypeException if the bean is not of the required type
     * @throws org.springframework.beans.BeansException if the bean could not be created
     */
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ac.getBean(name, requiredType);
    }

    /**
     * Return the parent bean factory, or {@code null} if there is none.
     */
    public static BeanFactory getParentBeanFactory() {
        return ac.getParentBeanFactory();
    }

    /**
     * Is this bean a shared singleton? That is, will {@link #getBean} always
     * return the same instance?
     * <p>Note: This method returning {@code false} does not clearly indicate
     * independent instances. It indicates non-singleton instances, which may correspond
     * to a scoped bean as well. Use the {@link #isPrototype} operation to explicitly
     * check for independent instances.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to query
     * @return whether this bean corresponds to a singleton instance
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no bean with the given name
     * @see #getBean
     * @see #isPrototype
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return ac.isSingleton(name);
    }

    /**
     * Return a Resource handle for the specified resource.
     * The handle should always be a reusable resource descriptor,
     * allowing for multiple {@link org.springframework.core.io.Resource#getInputStream()} calls.
     * <p><ul>
     * <li>Must support fully qualified URLs, e.g. "file:C:/test.dat".
     * <li>Must support classpath pseudo-URLs, e.g. "classpath:test.dat".
     * <li>Should support relative file paths, e.g. "WEB-INF/test.dat".
     * (This will be implementation-specific, typically provided by an
     * ApplicationContext implementation.)
     * </ul>
     * <p>Note that a Resource handle does not imply an existing resource;
     * you need to invoke {@link org.springframework.core.io.Resource#exists} to check for existence.
     * @param location the resource location
     * @return a corresponding Resource handle
     * @see #CLASSPATH_URL_PREFIX
     * @see org.springframework.core.io.Resource#exists
     * @see org.springframework.core.io.Resource#getInputStream
     */
    public static Resource getResource(String location) {
        return ac.getResource(location);
    }

    /**
     * Find all beans whose {@code Class} has the supplied {@link java.lang.annotation.Annotation} type.
     * @param annotationType the type of annotation to look for
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws org.springframework.beans.BeansException if a bean could not be created
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
        return ac.getBeansWithAnnotation(annotationType);
    }

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of {@code getBeanNamesForType} matches all kinds of beans,
     * be it singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeanNamesOfType(type, true, true)}.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all bean names
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see org.springframework.beans.factory.FactoryBean#getObjectType
     * @see org.springframework.beans.factory.BeanFactoryUtils#beanNamesForTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class)
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return ac.getBeanNamesForType(type);
    }

    /**
     * Return a name for the deployed application that this context belongs to.
     * @return a name for the deployed application, or the empty String by default
     */
    public static String getApplicationName() {
        return ac.getApplicationName();
    }

    /**
     * Find a {@link java.lang.annotation.Annotation} of {@code annotationType} on the specified
     * bean, traversing its interfaces and super classes if no annotation can be
     * found on the given class itself.
     * @param beanName the name of the bean to look for annotations on
     * @param annotationType the annotation class to look for
     * @return the annotation of the given type found, or {@code null}
     */
    public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
        return ac.findAnnotationOnBean(beanName, annotationType);
    }

    /**
     * Try to resolve the message. Return default message if no message was found.
     * @param code the code to lookup up, such as 'calculator.noRateSet'. Users of
     * this class are encouraged to base message names on the relevant fully
     * qualified class name, thus avoiding conflict and ensuring maximum clarity.
     * @param args array of arguments that will be filled in for params within
     * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     * or {@code null} if none.
     * @param defaultMessage String to return if the lookup fails
     * @param locale the Locale in which to do the lookup
     * @return the resolved message if the lookup was successful;
     * otherwise the default message passed as a parameter
     * @see java.text.MessageFormat
     */
    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return ac.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * Return the number of beans defined in the factory.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @return the number of beans defined in the factory
     */
    public static int getBeanDefinitionCount() {
        return ac.getBeanDefinitionCount();
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>This method allows a Spring BeanFactory to be used as a replacement for the
     * Singleton or Prototype design pattern. Callers may retain references to
     * returned objects in the case of Singleton beans.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to retrieve
     * @return an instance of the bean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no bean definition
     * with the specified name
     * @throws org.springframework.beans.BeansException if the bean could not be obtained
     */
    public static Object getBean(String name) throws BeansException {
        return ac.getBean(name);
    }

    /**
     * Notify all listeners registered with this application of an application
     * event. Events may be framework events (such as RequestHandledEvent)
     * or application-specific events.
     * @param event the event to publish
     * @see org.springframework.web.context.support.RequestHandledEvent
     */
    public static void publishEvent(ApplicationEvent event) {
        ac.publishEvent(event);
    }

    /**
     * Return an instance, which may be shared or independent, of the specified bean.
     * <p>Allows for specifying explicit constructor arguments / factory method arguments,
     * overriding the specified default arguments (if any) in the bean definition.
     * @param name the name of the bean to retrieve
     * @param args arguments to use if creating a prototype using explicit arguments to a
     * static factory method. It is invalid to use a non-null args value in any other case.
     * @return an instance of the bean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no such bean definition
     * @throws org.springframework.beans.factory.BeanDefinitionStoreException if arguments have been given but
     * the affected bean isn't a prototype
     * @throws org.springframework.beans.BeansException if the bean could not be created
     * @since 2.5
     */
    public static Object getBean(String name, Object... args) throws BeansException {
        return ac.getBean(name, args);
    }

    /**
     * Return the parent context, or {@code null} if there is no parent
     * and this is the root of the context hierarchy.
     * @return the parent context, or {@code null} if there is no parent
     */
    public static ApplicationContext getParent() {
        return ac.getParent();
    }

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans, which means that FactoryBeans
     * will get initialized. If the object created by the FactoryBean doesn't match,
     * the raw FactoryBean itself will be matched against the type.
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>This version of getBeansOfType matches all kinds of beans, be it
     * singletons, prototypes, or FactoryBeans. In most implementations, the
     * result will be the same as for {@code getBeansOfType(type, true, true)}.
     * <p>The Map returned by this method should always return bean names and
     * corresponding bean instances <i>in the order of definition</i> in the
     * backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all concrete beans
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws org.springframework.beans.BeansException if a bean could not be created
     * @since 1.1.2
     * @see org.springframework.beans.factory.FactoryBean#getObjectType
     * @see org.springframework.beans.factory.BeanFactoryUtils#beansOfTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class)
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return ac.getBeansOfType(type);
    }

    /**
     * Return a friendly name for this context.
     * @return a display name for this context (never {@code null})
     */
    public static String getDisplayName() {
        return ac.getDisplayName();
    }

    /**
     * Does this bean factory contain a bean definition or externally registered singleton
     * instance with the given name?
     * <p>If the given name is an alias, it will be translated back to the corresponding
     * canonical bean name.
     * <p>If this factory is hierarchical, will ask any parent factory if the bean cannot
     * be found in this factory instance.
     * <p>If a bean definition or singleton instance matching the given name is found,
     * this method will return {@code true} whether the named bean definition is concrete
     * or abstract, lazy or eager, in scope or not. Therefore, note that a {@code true}
     * return value from this method does not necessarily indicate that {@link #getBean}
     * will be able to obtain an instance for the same name.
     * @param name the name of the bean to query
     * @return whether a bean with the given name is present
     */
    public static boolean containsBean(String name) {
        return ac.containsBean(name);
    }

    /**
     * Is this bean a prototype? That is, will {@link #getBean} always return
     * independent instances?
     * <p>Note: This method returning {@code false} does not clearly indicate
     * a singleton object. It indicates non-independent instances, which may correspond
     * to a scoped bean as well. Use the {@link #isSingleton} operation to explicitly
     * check for a shared singleton instance.
     * <p>Translates aliases back to the corresponding canonical bean name.
     * Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the name of the bean to query
     * @return whether this bean will always deliver independent instances
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if there is no bean with the given name
     * @since 2.0.3
     * @see #getBean
     * @see #isSingleton
     */
    public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return ac.isPrototype(name);
    }

    /**
     * Expose the ClassLoader used by this ResourceLoader.
     * <p>Clients which need to access the ClassLoader directly can do so
     * in a uniform manner with the ResourceLoader, rather than relying
     * on the thread context ClassLoader.
     * @return the ClassLoader (never {@code null})
     */
    public static ClassLoader getClassLoader() {
        return ac.getClassLoader();
    }

    /**
     * Check if this bean factory contains a bean definition with the given name.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @param beanName the name of the bean to look for
     * @return if this bean factory contains a bean definition with the given name
     * @see #containsBean
     */
    public static boolean containsBeanDefinition(String beanName) {
        return ac.containsBeanDefinition(beanName);
    }

    /**
     * Return the bean instances that match the given object type (including
     * subclasses), judging from either bean definitions or the value of
     * {@code getObjectType} in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beansOfTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>The Map returned by this method should always return bean names and
     * corresponding bean instances <i>in the order of definition</i> in the
     * backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all concrete beans
     * @param includeNonSingletons whether to include prototype or scoped beans too
     * or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether to initialize <i>lazy-init singletons</i> and
     * <i>objects created by FactoryBeans</i> (or by factory methods with a
     * "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     * eagerly initialized to determine their type: So be aware that passing in "true"
     * for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return a Map with the matching beans, containing the bean names as
     * keys and the corresponding bean instances as values
     * @throws org.springframework.beans.BeansException if a bean could not be created
     * @see org.springframework.beans.factory.FactoryBean#getObjectType
     * @see org.springframework.beans.factory.BeanFactoryUtils#beansOfTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class, boolean, boolean)
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
        return ac.getBeansOfType(type, includeNonSingletons, allowEagerInit);
    }

    /**
     * Try to resolve the message using all the attributes contained within the
     * {@code MessageSourceResolvable} argument that was passed in.
     * <p>NOTE: We must throw a {@code NoSuchMessageException} on this method
     * since at the time of calling this method we aren't able to determine if the
     * {@code defaultMessage} property of the resolvable is null or not.
     * @param resolvable value object storing attributes required to properly resolve a message
     * @param locale the Locale in which to do the lookup
     * @return the resolved message
     * @throws org.springframework.context.NoSuchMessageException if the message wasn't found
     * @see java.text.MessageFormat
     */
    public static String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return ac.getMessage(resolvable, locale);
    }

    /**
     * Return the names of beans matching the given type (including subclasses),
     * judging from either bean definitions or the value of {@code getObjectType}
     * in the case of FactoryBeans.
     * <p><b>NOTE: This method introspects top-level beans only.</b> It does <i>not</i>
     * check nested beans which might match the specified type as well.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit" flag is set,
     * which means that FactoryBeans will get initialized. If the object created by the
     * FactoryBean doesn't match, the raw FactoryBean itself will be matched against the
     * type. If "allowEagerInit" is not set, only raw FactoryBeans will be checked
     * (which doesn't require initialization of each FactoryBean).
     * <p>Does not consider any hierarchy this factory may participate in.
     * Use BeanFactoryUtils' {@code beanNamesForTypeIncludingAncestors}
     * to include beans in ancestor factories too.
     * <p>Note: Does <i>not</i> ignore singleton beans that have been registered
     * by other means than bean definitions.
     * <p>Bean names returned by this method should always return bean names <i>in the
     * order of definition</i> in the backend configuration, as far as possible.
     * @param type the class or interface to match, or {@code null} for all bean names
     * @param includeNonSingletons whether to include prototype or scoped beans too
     * or just singletons (also applies to FactoryBeans)
     * @param allowEagerInit whether to initialize <i>lazy-init singletons</i> and
     * <i>objects created by FactoryBeans</i> (or by factory methods with a
     * "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     * eagerly initialized to determine their type: So be aware that passing in "true"
     * for this flag will initialize FactoryBeans and "factory-bean" references.
     * @return the names of beans (or objects created by FactoryBeans) matching
     * the given object type (including subclasses), or an empty array if none
     * @see org.springframework.beans.factory.FactoryBean#getObjectType
     * @see org.springframework.beans.factory.BeanFactoryUtils#beanNamesForTypeIncludingAncestors(org.springframework.beans.factory.ListableBeanFactory, Class, boolean, boolean)
     */
    public static String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return ac.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
    }

    /**
     * Resolve the given location pattern into Resource objects.
     * <p>Overlapping resource entries that point to the same physical
     * resource should be avoided, as far as possible. The result should
     * have set semantics.
     * @param locationPattern the location pattern to resolve
     * @return the corresponding Resource objects
     * @throws java.io.IOException in case of I/O errors
     */
    public static Resource[] getResources(String locationPattern) throws IOException {
        return ac.getResources(locationPattern);
    }

    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     * @param requiredType type the bean must match; can be an interface or superclass.
     * {@code null} is disallowed.
     * <p>This method goes into {@link org.springframework.beans.factory.ListableBeanFactory} by-type lookup territory
     * but may also be translated into a conventional by-name lookup based on the name
     * of the given type. For more extensive retrieval operations across sets of beans,
     * use {@link org.springframework.beans.factory.ListableBeanFactory} and/or {@link org.springframework.beans.factory.BeanFactoryUtils}.
     * @return an instance of the single bean matching the required type
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException if no bean of the given type was found
     * @throws org.springframework.beans.factory.NoUniqueBeanDefinitionException if more than one bean of the given type was found
     * @since 3.0
     * @see org.springframework.beans.factory.ListableBeanFactory
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return ac.getBean(requiredType);
    }

    /**
     * Return the names of all beans defined in this factory.
     * <p>Does not consider any hierarchy this factory may participate in,
     * and ignores any singleton beans that have been registered by
     * other means than bean definitions.
     * @return the names of all beans defined in this factory,
     * or an empty array if none defined
     */
    public static String[] getBeanDefinitionNames() {
        return ac.getBeanDefinitionNames();
    }

    /**
     * Return whether the local bean factory contains a bean of the given name,
     * ignoring beans defined in ancestor contexts.
     * <p>This is an alternative to {@code containsBean}, ignoring a bean
     * of the given name from an ancestor bean factory.
     * @param name the name of the bean to query
     * @return whether a bean with the given name is defined in the local factory
     * @see org.springframework.beans.factory.BeanFactory#containsBean
     */
    public static boolean containsLocalBean(String name) {
        return ac.containsLocalBean(name);
    }

    /**
     * Try to resolve the message. Treat as an error if the message can't be found.
     * @param code the code to lookup up, such as 'calculator.noRateSet'
     * @param args Array of arguments that will be filled in for params within
     * the message (params look like "{0}", "{1,date}", "{2,time}" within a message),
     * or {@code null} if none.
     * @param locale the Locale in which to do the lookup
     * @return the resolved message
     * @throws org.springframework.context.NoSuchMessageException if the message wasn't found
     * @see java.text.MessageFormat
     */
    public static String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return ac.getMessage(code, args, locale);
    }

    /**
     * Return the aliases for the given bean name, if any.
     * All of those aliases point to the same bean when used in a {@link #getBean} call.
     * <p>If the given name is an alias, the corresponding original bean name
     * and other aliases (if any) will be returned, with the original bean name
     * being the first element in the array.
     * <p>Will ask the parent factory if the bean cannot be found in this factory instance.
     * @param name the bean name to check for aliases
     * @return the aliases, or an empty array if none
     * @see #getBean
     */
    public static String[] getAliases(String name) {
        return ac.getAliases(name);
    }

    private static void init(){
        if(!isInit){
            synchronized (isInit){
                if(!isInit){
                    ac = new ClassPathXmlApplicationContext("application.xml");

                }
            }
        }
    }

    static {
        init();
    }
}
