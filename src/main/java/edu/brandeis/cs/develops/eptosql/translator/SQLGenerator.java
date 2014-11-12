package edu.brandeis.cs.develops.eptosql.translator;

public class SQLGenerator {

    public static String createSQL(Relation fullRelation) {
        String sql = String.format("%s OPTION(FORCE ORDER);", nestedSQL(fullRelation));
        return sql;
    }

    private static String nestedSQL(Relation relation) {
        if (relation instanceof Join) {
            return nestedSQL((Join) relation);
        } else if (relation instanceof Table) {
            return nestedSQL((Table) relation);
        } else if (relation instanceof Selection) {
            return nestedSQL((Selection) relation);
        } else {
            throw new UnsupportedOperationException("Unrecognized type");
        }
    }

    private static String nestedSQL(Join relation) {
        String result = String.format("SELECT * FROM (%s) table_left %s JOIN (%s) table_right ON (%s)", nestedSQL(relation.getLeftChild()), relation.getJoinType(), nestedSQL(relation.getRightChild()), relation.getPredicate());
        return result;
    }

    private static String nestedSQL(Selection relation) {
        String result = String.format("SELECT * FROM (%s) table_inner WHERE %s", nestedSQL(relation.getChild()), relation.getPredicate());
        return result;
    }

    private static String nestedSQL(Table relation) {
        String result = String.format("SELECT * FROM %s", relation.getName());
        return result;
    }

    private class SQLBuildObj {

        StringBuilder from = new StringBuilder();
        StringBuilder joins = new StringBuilder();
        StringBuilder where = new StringBuilder();

        public SQLBuildObj() {
        }

        private void evaluate(Relation relation) {
            if (relation instanceof Join) {
                evaluate((Join) relation);
            }
            if (relation instanceof Table) {
                evaluate((Table) relation);
            }
            if (relation instanceof Selection) {
                evaluate((Selection) relation);
            }
        }

        private void evaluate(Join relation) {
            if (relation.getLeftChild() instanceof Table) {
                evaluate(relation.getRightChild());
                joins.append(" ");
                joins.append(relation.getJoinType());
                joins.append(((Table) relation.getLeftChild()).getName());
            } else if (relation.getRightChild() instanceof Table) {
                evaluate(relation.getLeftChild());
                joins.append(" ");
                joins.append(relation.getJoinType());
                joins.append(((Table) relation.getRightChild()).getName());
            } else {
                evaluate(relation.getLeftChild());
                evaluate(relation.getRightChild());
            }

        }

        private void evaluate(Table relation) {
            from.append(relation.getName());
        }

        private void evaluate(Selection relation) {
            if (where.length() > 0) {
                where.append(" AND ");
            }
            where.append(relation.getPredicate());
        }
    }
}
