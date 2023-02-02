import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './catalogue.reducer';

export const CatalogueDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const catalogueEntity = useAppSelector(state => state.mytrackit.catalogue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="catalogueDetailsHeading">
          <Translate contentKey="myTrackitApp.catalogue.detail.title">Catalogue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.id}</dd>
          <dt>
            <span id="materialCode">
              <Translate contentKey="myTrackitApp.catalogue.materialCode">Material Code</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.materialCode}</dd>
          <dt>
            <span id="materialDesc">
              <Translate contentKey="myTrackitApp.catalogue.materialDesc">Material Desc</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.materialDesc}</dd>
          <dt>
            <span id="materialGroup">
              <Translate contentKey="myTrackitApp.catalogue.materialGroup">Material Group</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.materialGroup}</dd>
        </dl>
        <Button tag={Link} to="/catalogue" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/catalogue/${catalogueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CatalogueDetail;
