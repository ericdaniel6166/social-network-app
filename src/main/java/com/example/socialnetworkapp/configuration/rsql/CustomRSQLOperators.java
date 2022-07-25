package com.example.socialnetworkapp.configuration.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

public abstract class CustomRSQLOperators {
    public static final ComparisonOperator BOOLEAN = new ComparisonOperator("=bool=");

}
