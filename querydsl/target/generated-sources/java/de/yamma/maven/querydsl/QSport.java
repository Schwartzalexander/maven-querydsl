package de.yamma.maven.querydsl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSport is a Querydsl query type for Sport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSport extends EntityPathBase<Sport> {

    private static final long serialVersionUID = 1371344000L;

    public static final QSport sport = new QSport("sport");

    public final BooleanPath hasDistance = createBoolean("hasDistance");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final BooleanPath uploaded = createBoolean("uploaded");

    public QSport(String variable) {
        super(Sport.class, forVariable(variable));
    }

    public QSport(Path<? extends Sport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSport(PathMetadata metadata) {
        super(Sport.class, metadata);
    }

}

