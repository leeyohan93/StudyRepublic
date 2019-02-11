/**
 * 
 */
package org.mohajo.studyrepublic.security;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

/**
 * @author 윤성호
 * @since 2019.01.22
 * @version -기능1 추가 -기능2 추가 -기능3 추가
 */

public final class HiberNate {

	public static void initialize(Object proxy) throws HibernateException { 
		if ( proxy == null ) { return; }
		else if ( proxy instanceof HibernateProxy ) {
			( ( HibernateProxy ) proxy ).getHibernateLazyInitializer().initialize();		
		}

		else if ( proxy instanceof PersistentCollection ) { 
			( (PersistentCollection) proxy ).forceInitialization(); 
			}

		
	
	}

}
