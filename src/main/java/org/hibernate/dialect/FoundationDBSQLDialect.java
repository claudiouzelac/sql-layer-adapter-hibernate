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
package org.hibernate.dialect;

import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.MappingException;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.internal.util.JdbcExceptionHelper;
import org.hibernate.type.StandardBasicTypes;

public class FoundationDBSQLDialect extends Dialect {

    public FoundationDBSQLDialect () {
        super();

        registerColumnType( Types.VARBINARY, "varchar($l) for bit data" );
        registerColumnType( Types.LONGVARBINARY, "varchar($l) for bit data" );
        registerColumnType( Types.BINARY, 1024, "char($l) for bit data");
        registerColumnType( Types.BINARY, "char(1024) for bit data");

        registerFunction( "ascii", new StandardSQLFunction( "ascii", StandardBasicTypes.INTEGER ) );
        registerFunction( "bin", new StandardSQLFunction( "bin", StandardBasicTypes.STRING ) );
        registerFunction( "char_length", new StandardSQLFunction( "char_length", StandardBasicTypes.LONG ) );
        registerFunction( "character_length", new StandardSQLFunction( "character_length", StandardBasicTypes.LONG ) );
        registerFunction( "lcase", new StandardSQLFunction( "lcase" ) );
        registerFunction( "lower", new StandardSQLFunction( "lower" ) );
        registerFunction( "ltrim", new StandardSQLFunction( "ltrim" ) );
        registerFunction( "ord", new StandardSQLFunction( "ord", StandardBasicTypes.INTEGER ) );
        registerFunction( "quote", new StandardSQLFunction( "quote" ) );
        registerFunction( "reverse", new StandardSQLFunction( "reverse" ) );
        registerFunction( "rtrim", new StandardSQLFunction( "rtrim" ) );
        registerFunction( "soundex", new StandardSQLFunction( "soundex" ) );
        registerFunction( "space", new StandardSQLFunction( "space", StandardBasicTypes.STRING ) );
        registerFunction( "ucase", new StandardSQLFunction( "ucase" ) );
        registerFunction( "upper", new StandardSQLFunction( "upper" ) );
        registerFunction( "unhex", new StandardSQLFunction( "unhex", StandardBasicTypes.STRING ) );

        registerFunction( "abs", new StandardSQLFunction( "abs" ) );
        registerFunction( "sign", new StandardSQLFunction( "sign", StandardBasicTypes.INTEGER ) );

        registerFunction( "acos", new StandardSQLFunction( "acos", StandardBasicTypes.DOUBLE ) );
        registerFunction( "asin", new StandardSQLFunction( "asin", StandardBasicTypes.DOUBLE ) );
        registerFunction( "atan", new StandardSQLFunction( "atan", StandardBasicTypes.DOUBLE ) );
        registerFunction( "cos", new StandardSQLFunction( "cos", StandardBasicTypes.DOUBLE ) );
        registerFunction( "cot", new StandardSQLFunction( "cot", StandardBasicTypes.DOUBLE ) );
        registerFunction( "crc32", new StandardSQLFunction( "crc32", StandardBasicTypes.LONG ) );
        registerFunction( "exp", new StandardSQLFunction( "exp", StandardBasicTypes.DOUBLE ) );
        registerFunction( "ln", new StandardSQLFunction( "ln", StandardBasicTypes.DOUBLE ) );
        registerFunction( "log", new StandardSQLFunction( "log", StandardBasicTypes.DOUBLE ) );
        registerFunction( "log2", new StandardSQLFunction( "log2", StandardBasicTypes.DOUBLE ) );
        registerFunction( "log10", new StandardSQLFunction( "log10", StandardBasicTypes.DOUBLE ) );
        registerFunction( "pi", new NoArgSQLFunction( "pi", StandardBasicTypes.DOUBLE ) );
        registerFunction( "rand", new NoArgSQLFunction( "rand", StandardBasicTypes.DOUBLE ) );
        registerFunction( "sin", new StandardSQLFunction( "sin", StandardBasicTypes.DOUBLE ) );
        registerFunction( "sqrt", new StandardSQLFunction( "sqrt", StandardBasicTypes.DOUBLE ) );
        registerFunction( "stddev", new StandardSQLFunction("std", StandardBasicTypes.DOUBLE) );
        registerFunction( "tan", new StandardSQLFunction( "tan", StandardBasicTypes.DOUBLE ) );

        registerFunction( "radians", new StandardSQLFunction( "radians", StandardBasicTypes.DOUBLE ) );
        registerFunction( "degrees", new StandardSQLFunction( "degrees", StandardBasicTypes.DOUBLE ) );

        registerFunction( "ceiling", new StandardSQLFunction( "ceiling", StandardBasicTypes.INTEGER ) );
        registerFunction( "ceil", new StandardSQLFunction( "ceil", StandardBasicTypes.INTEGER ) );
        registerFunction( "floor", new StandardSQLFunction( "floor", StandardBasicTypes.INTEGER ) );
        registerFunction( "round", new StandardSQLFunction( "round" ) );

        registerFunction( "datediff", new StandardSQLFunction( "datediff", StandardBasicTypes.INTEGER ) );
        registerFunction( "timediff", new StandardSQLFunction( "timediff", StandardBasicTypes.TIME ) );
        registerFunction( "date_format", new StandardSQLFunction( "date_format", StandardBasicTypes.STRING ) );

        registerFunction( "curdate", new NoArgSQLFunction( "curdate", StandardBasicTypes.DATE ) );
        registerFunction( "curtime", new NoArgSQLFunction( "curtime", StandardBasicTypes.TIME ) );
        registerFunction( "current_date", new NoArgSQLFunction( "current_date", StandardBasicTypes.DATE, false ) );
        registerFunction( "current_time", new NoArgSQLFunction( "current_time", StandardBasicTypes.TIME, false ) );
        registerFunction( "current_timestamp", new NoArgSQLFunction( "current_timestamp", StandardBasicTypes.TIMESTAMP, false ) );
        registerFunction( "date", new StandardSQLFunction( "date", StandardBasicTypes.DATE ) );
        registerFunction( "day", new StandardSQLFunction( "day", StandardBasicTypes.INTEGER ) );
        registerFunction( "dayofmonth", new StandardSQLFunction( "dayofmonth", StandardBasicTypes.INTEGER ) );
        registerFunction( "dayname", new StandardSQLFunction( "dayname", StandardBasicTypes.STRING ) );
        registerFunction( "dayofweek", new StandardSQLFunction( "dayofweek", StandardBasicTypes.INTEGER ) );
        registerFunction( "dayofyear", new StandardSQLFunction( "dayofyear", StandardBasicTypes.INTEGER ) );
        registerFunction( "from_days", new StandardSQLFunction( "from_days", StandardBasicTypes.DATE ) );
        registerFunction( "from_unixtime", new StandardSQLFunction( "from_unixtime", StandardBasicTypes.TIMESTAMP ) );
        registerFunction( "hour", new StandardSQLFunction( "hour", StandardBasicTypes.INTEGER ) );
        registerFunction( "last_day", new StandardSQLFunction( "last_day", StandardBasicTypes.DATE ) );
        registerFunction( "localtime", new NoArgSQLFunction( "localtime", StandardBasicTypes.TIMESTAMP ) );
        registerFunction( "localtimestamp", new NoArgSQLFunction( "localtimestamp", StandardBasicTypes.TIMESTAMP ) );
        registerFunction( "microseconds", new StandardSQLFunction( "microseconds", StandardBasicTypes.INTEGER ) );
        registerFunction( "minute", new StandardSQLFunction( "minute", StandardBasicTypes.INTEGER ) );
        registerFunction( "month", new StandardSQLFunction( "month", StandardBasicTypes.INTEGER ) );
        registerFunction( "monthname", new StandardSQLFunction( "monthname", StandardBasicTypes.STRING ) );
        registerFunction( "now", new NoArgSQLFunction( "now", StandardBasicTypes.TIMESTAMP ) );
        registerFunction( "quarter", new StandardSQLFunction( "quarter", StandardBasicTypes.INTEGER ) );
        registerFunction( "second", new StandardSQLFunction( "second", StandardBasicTypes.INTEGER ) );
        registerFunction( "sec_to_time", new StandardSQLFunction( "sec_to_time", StandardBasicTypes.TIME ) );
        registerFunction( "sysdate", new NoArgSQLFunction( "sysdate", StandardBasicTypes.TIMESTAMP ) );
        registerFunction( "time", new StandardSQLFunction( "time", StandardBasicTypes.TIME ) );
        registerFunction( "timestamp", new StandardSQLFunction( "timestamp", StandardBasicTypes.TIMESTAMP ) );
        registerFunction( "time_to_sec", new StandardSQLFunction( "time_to_sec", StandardBasicTypes.INTEGER ) );
        registerFunction( "to_days", new StandardSQLFunction( "to_days", StandardBasicTypes.LONG ) );
        registerFunction( "unix_timestamp", new StandardSQLFunction( "unix_timestamp", StandardBasicTypes.LONG ) );
        registerFunction( "utc_date", new NoArgSQLFunction( "utc_date", StandardBasicTypes.STRING ) );
        registerFunction( "utc_time", new NoArgSQLFunction( "utc_time", StandardBasicTypes.STRING ) );
        registerFunction( "utc_timestamp", new NoArgSQLFunction( "utc_timestamp", StandardBasicTypes.STRING ) );
        registerFunction( "week", new StandardSQLFunction( "week", StandardBasicTypes.INTEGER ) );
        registerFunction( "weekday", new StandardSQLFunction( "weekday", StandardBasicTypes.INTEGER ) );
        registerFunction( "weekofyear", new StandardSQLFunction( "weekofyear", StandardBasicTypes.INTEGER ) );
        registerFunction( "year", new StandardSQLFunction( "year", StandardBasicTypes.INTEGER ) );
        registerFunction( "yearweek", new StandardSQLFunction( "yearweek", StandardBasicTypes.INTEGER ) );

        registerFunction( "hex", new StandardSQLFunction( "hex", StandardBasicTypes.STRING ) );
        registerFunction( "oct", new StandardSQLFunction( "oct", StandardBasicTypes.STRING ) );

        registerFunction( "octet_length", new StandardSQLFunction( "octet_length", StandardBasicTypes.LONG ) );
        registerFunction( "bit_length", new StandardSQLFunction( "bit_length", StandardBasicTypes.LONG ) );

        registerFunction( "bit_count", new StandardSQLFunction( "bit_count", StandardBasicTypes.LONG ) );
        registerFunction( "encrypt", new StandardSQLFunction( "encrypt", StandardBasicTypes.STRING ) );
        registerFunction( "md5", new StandardSQLFunction( "md5", StandardBasicTypes.STRING ) );
        registerFunction( "sha1", new StandardSQLFunction( "sha1", StandardBasicTypes.STRING ) );
        registerFunction( "sha", new StandardSQLFunction( "sha", StandardBasicTypes.STRING ) );

        registerFunction( "concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", "") );
        registerFunction( "str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as varchar(8192))") );

        //registerFunction( "concat", new StandardSQLFunction( "concat", StandardBasicTypes.STRING ) );

        registerKeyword("year");
        registerKeyword("user");
    }

    
    @Override
    public boolean useInputStreamToInsertBlob() {
        return false;
    }
    
    @Override
    public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
        return EXTRACTER;
    }

    /**
     * Constraint-name extractor for Postgres constraint violation exceptions.
     * Orginally contributed by Denny Bartelt.
     */
    private static final ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter() {
        public String extractConstraintName(SQLException sqle) {
            try {
                final int sqlState = Integer.valueOf( JdbcExceptionHelper.extractSqlState( sqle ) );
                switch (sqlState) {
                    // CHECK VIOLATION - TODO: When added
                    //case 23514: return extractUsingTemplate( "violates check constraint \"","\"", sqle.getMessage() );
                    // UNIQUE VIOLATION
                   case 23501: return extractUsingTemplate( "violates unique constraint ","\"", sqle.getMessage() );
                    // FOREIGN KEY VIOLATION
                    case 23503: return extractUsingTemplate( "due to foreign key constraint "," on ", sqle.getMessage() );
                    // NOT NULL VIOLATION
                    case 23502: return extractUsingTemplate( "NULL value not permitted for ","\"", sqle.getMessage() );
                    // TODO: RESTRICT VIOLATION
                    //case 23001: return null;
                    // ALL OTHER
                    default: return null;
                }
            }
            catch (NumberFormatException nfe) {
                return null;
            }
        }
    };


    // Overridden DENTITY support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Does this dialect support identity column key generation?
     *
     * @return True if IDENTITY columns are supported; false otherwise.
     */
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }
    
    @Override
    public String getIdentityColumnString(int type) {
        return "serial not null";
    }

    @Override
    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }

    
    // SEQUENCE support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Does this dialect support sequences?
     *
     * @return True if sequences supported; false otherwise.
     */
    @Override
    public boolean supportsSequences() {
        return true;
    }

    /**
     * Does this dialect support "pooled" sequences.  Not aware of a better
     * name for this.  Essentially can we specify the initial and increment values?
     *
     * @return True if such "pooled" sequences are supported; false otherwise.
     * @see #getCreateSequenceStrings(String, int, int)
     * @see #getCreateSequenceString(String, int, int)
     */
    @Override
    public boolean supportsPooledSequences() {
        return true;
    }
    /**
     * Generate the appropriate select statement to to retrieve the next value
     * of a sequence.
     * <p/>
     * This should be a "stand alone" select statement.
     *
     * @param sequenceName the name of the sequence
     * @return String The "nextval" select string.
     * @throws MappingException If sequences are not supported.
     */
    @Override
    public String getSequenceNextValString(String sequenceName) {
        return "select " + getSelectSequenceNextValString( sequenceName );
    }

    /**
     * Generate the select expression fragment that will retrieve the next
     * value of a sequence as part of another (typically DML) statement.
     * <p/>
     * This differs from {@link #getSequenceNextValString(String)} in that this
     * should return an expression usable within another statement.
     *
     * @param sequenceName the name of the sequence
     * @return The "nextval" fragment.
     * @throws MappingException If sequences are not supported.
     */
    @Override
    public String getSelectSequenceNextValString(String sequenceName) {
        return "NEXT VALUE FOR " + sequenceName;
    }

    /**
     * Typically dialects which support sequences can create a sequence
     * with a single command.  This is convenience form of
     * {@link #getCreateSequenceStrings} to help facilitate that.
     * <p/>
     * Dialects which support sequences and can create a sequence in a
     * single command need *only* override this method.  Dialects
     * which support sequences but require multiple commands to create
     * a sequence should instead override {@link #getCreateSequenceStrings}.
     *
     * @param sequenceName The name of the sequence
     * @return The sequence creation command
     * @throws MappingException If sequences are not supported.
     */
    @Override
    protected String getCreateSequenceString(String sequenceName) throws MappingException {
        return getCreateSequenceString (sequenceName, 1, 1);
    }

    protected String getCreateSequenceString(String sequenceName, int initialValue, int incrementSize) throws MappingException {
            return "create sequence " + sequenceName + " start with " + initialValue + " increment by " + incrementSize;
    }


    @Override
    public String getDropSequenceString(String sequenceName) {
        return "drop sequence " + sequenceName + " restrict";
    }
    
    /**
     * Get the select command used retrieve the names of all sequences.
     *
     * @return The select command; or null if sequences are not supported.
     * @see org.hibernate.tool.hbm2ddl.SchemaUpdate
     */
    @Override
    public String getQuerySequencesString() {
        return "select sequence_name from information_schema.sequences";
    }

    @Override
    public Class getNativeIdentifierGeneratorClass() {
        return SequenceGenerator.class;
    }

    // Overridden limit/offset support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Does this dialect support some form of limiting query results
     * via a SQL clause?
     *
     * @return True if this dialect supports some form of LIMIT.
     * @deprecated {@link #buildLimitHandler(String, RowSelection)} should be overridden instead.
     */
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, boolean hasOffset) {
        return sql + (hasOffset ? " limit ? offset ?" : " limit ?");
    }

    @Override
    public boolean bindLimitParametersInReverseOrder() {
        return true;
    }

    // Overridden current timestamp support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Does this dialect support a way to retrieve the database's current
     * timestamp value?
     *
     * @return True if the current timestamp can be retrieved; false otherwise.
     */
    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    /**
     * Should the value returned by {@link #getCurrentTimestampSelectString}
     * be treated as callable.  Typically this indicates that JDBC escape
     * syntax is being used...
     *
     * @return True if the {@link #getCurrentTimestampSelectString} return
     * is callable; false otherwise.
     */
    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    /**
     * Retrieve the command used to retrieve the current timestamp from the
     * database.
     *
     * @return The command.
     */
    @Override
    public String getCurrentTimestampSelectString() {
        return "select now()";
    }

    // overridden miscellaneous support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The fragment used to insert a row without specifying any column values.
     * This is not possible on some databases.
     *
     * @return The appropriate empty values clause.
     */
    public String getNoColumnsInsertString() {
        return "values (DEFAULT)";
    }

    /**
     * What is the maximum length Hibernate can use for generated aliases?
     * <p/>
     * The maximum here should account for the fact that Hibernate often needs to append "uniqueing" information
     * to the end of generated aliases.  That "uniqueing" information will be added to the end of a identifier
     * generated to the length specified here; so be sure to leave some room (generally speaking 5 positions will
     * suffice).
     *
     * @return The maximum length.
     */
    public int getMaxAliasLength() {
        return 100;
    }

    
    // overridden union subclass support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Does this dialect support UNION ALL, which is generally a faster
     * variant of UNION?
     *
     * @return True if UNION ALL is supported; false otherwise.
     */
    public boolean supportsUnionAll() {
        return true;
    }

    // overridden lock acquisition support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the string to append to SELECT statements to acquire locks
     * for this dialect.
     *
     * @return The appropriate <tt>FOR UPDATE</tt> clause string.
     */
    public String getForUpdateString() {
        return "";
    }

    // overridden temporary table support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public boolean supportsTemporaryTables() {
        return false;
    }

    /**
     * Does the dialect require that temporary table DDL statements occur in
     * isolation from other statements?  This would be the case if the creation
     * would cause any current transaction to get committed implicitly.
     * <p/>
     * JDBC defines a standard way to query for this information via the
     * {@link java.sql.DatabaseMetaData#dataDefinitionCausesTransactionCommit()}
     * method.  However, that does not distinguish between temporary table
     * DDL and other forms of DDL; MySQL, for example, reports DDL causing a
     * transaction commit via its driver, even though that is not the case for
     * temporary table DDL.
     * <p/>
     * Possible return values and their meanings:<ul>
     * <li>{@link Boolean#TRUE} - Unequivocally, perform the temporary table DDL
     * in isolation.</li>
     */
    @Override
    public Boolean performTemporaryTableDDLInIsolation() {
        return null;
    }

    // Overridden DDL support ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   
    /**
     * For dropping a table, can the phrase "if exists" be applied before the table name?
     * <p/>
     * NOTE : Only one or the other (or neither) of this and {@link #supportsIfExistsAfterTableName} should return true
     *
     * @return {@code true} if the "if exists" can be applied before the table name
     */
    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }
    
    /**
     * The syntax used to add a column to a table (optional).
     *
     * @return The "add column" fragment.
     */
    public String getAddColumnString() {
        return "add column";
    }

    // Overridden Informational metadata ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Does this dialect support `count(distinct a,b)`?
     *
     * @return True if the database supports counting distinct tuples; false otherwise.
     */
    public boolean supportsTupleDistinctCounts() {
        // oddly most database in fact seem to, so true is the default.
        return false;
    }
    /**
     * Does this dialect support tuples in subqueries?  Ex:
     * delete from Table1 where (col1, col2) in (select col1, col2 from Table2)
     * 
     * @return boolean
     */
    public boolean supportsTuplesInSubqueries() {
        return false;
    }

    /**
     * Does this dialect support empty IN lists?
     * <p/>
     * For example, is [where XYZ in ()] a supported construct?
     *
     * @return True if empty in lists are supported; false otherwise.
     * @since 3.2
     */
    @Override
    public boolean supportsEmptyInList() {
        return false;
    }

    /**
     * Expected LOB usage pattern is such that I can perform an insert
     * via prepared statement with a parameter binding for a LOB value
     * without crazy casting to JDBC driver implementation-specific classes...
     */ 
    @Override
    public boolean supportsExpectedLobUsagePattern() {
        return false;
    }

    /**
     * Does this dialect support table-level check constraints?
     *
     * @return True if table-level CHECK constraints are supported; false
     * otherwise.
     */
    @Override
    public boolean supportsTableCheck() {
        return false;
    }
    /**
     * Does this dialect support column-level check constraints?
     *
     * @return True if column-level CHECK constraints are supported; false
     * otherwise.
     */
    @Override
    public boolean supportsColumnCheck() {
        return false;
    }
    /**
     * Negate an expression
     *
     * @param expression The expression to negate
     *
     * @return The negated expression
     */
    public String getNotExpression(String expression) {
        return "not (" + expression + ")";
    }

}
