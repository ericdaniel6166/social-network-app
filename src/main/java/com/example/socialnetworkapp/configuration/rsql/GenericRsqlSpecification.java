package com.example.socialnetworkapp.configuration.rsql;

import com.example.socialnetworkapp.utils.Constants;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GenericRsqlSpecification<T> implements Specification<T> {

    private final String property;
    private final ComparisonOperator operator;
    private final List<String> arguments;

    public GenericRsqlSpecification(final String property, final ComparisonOperator operator, final List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Object> args = castArguments(root);
        final Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(this.operator)) {

            case BOOLEAN:
                if (Constants.TRUE.equalsIgnoreCase(argument.toString())) {
                    return builder.isTrue(root.get(this.property).as(Boolean.class));
                } else if (Constants.FALSE.equalsIgnoreCase(argument.toString())) {
                    return builder.isFalse(root.get(this.property).as(Boolean.class));
                } else {
                    log.error("Argument value is inappropriate, property: {}, operator:{}, argument: {}", this.property, this.operator, argument);
                    throw new IllegalArgumentException("Argument value is inappropriate, property: " + this.property + ", operator: " + this.operator + ", argument: " + argument);
                }

            case EQUAL: {
                if (argument instanceof String) {
                    return builder.like(root.get(this.property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNull(root.get(this.property));
                } else {
                    return builder.equal(root.get(this.property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return builder.notLike(root.get(this.property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNotNull(root.get(this.property));
                } else {
                    return builder.notEqual(root.get(this.property), argument);
                }
            }
            case GREATER_THAN: {
                return builder.greaterThan(root.get(this.property), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                return builder.greaterThanOrEqualTo(root.get(this.property), argument.toString());
            }
            case LESS_THAN: {
                return builder.lessThan(root.get(this.property), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                return builder.lessThanOrEqualTo(root.get(this.property), argument.toString());
            }
            case IN:
                return root.get(this.property).in(args);
            case NOT_IN:
                return builder.not(root.get(this.property).in(args));
        }

        return null;
    }

    private List<Object> castArguments(final Root<T> root) {

        final Class<?> type = root.get(this.property).getJavaType();

        return this.arguments.stream().map(arg -> {
            if (type.equals(Integer.class)) {
                return Integer.parseInt(arg);
            } else if (type.equals(Long.class)) {
                return Long.parseLong(arg);
            } else {
                return arg;
            }
        }).collect(Collectors.toList());
    }

}
