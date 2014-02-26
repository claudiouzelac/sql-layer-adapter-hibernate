/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.cfg;

import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.StringHelper;

public class DialectSensitiveNamingStrategy extends EJB3NamingStrategy {

    /**
     * The singleton instance
     */
    public static final NamingStrategy INSTANCE = new DialectSensitiveNamingStrategy();


    @Override
    public String classToTableName(String className) {
        return keywordCheck(  StringHelper.unqualify(className) ) ;
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return keywordCheck( StringHelper.unqualify(propertyName) );
 
    }

    @Override
    public String tableName(String tableName) {
        return keywordCheck( tableName );
    }

    @Override
    public String columnName(String columnName) {
        return keywordCheck( columnName);
    }

    /**
     * Return the column name or the unqualified property name
     */
    public String logicalColumnName(String columnName, String propertyName) {
        return StringHelper.isNotEmpty( columnName ) ? columnName :  StringHelper.unqualify( propertyName );
    }


    /**
     * If the name being used is a dialect specific keyword, 
     * enclose it in the dialect specific quotes
     */
    protected static String keywordCheck(String name) {
        // TODO : Dialect should supply list of invalid characters (or valid ones)
        // and a replacement character if the invalid one occurs
        name = name.replace('$', '_');
        
        // Check dialect keywords and quote them as needed
        if (Dialect.getDialect().getKeywords().contains(name.toLowerCase())) {
            Dialect d = Dialect.getDialect();
            name = d.openQuote() + name + d.closeQuote();
        }
        return name;
    }
}
