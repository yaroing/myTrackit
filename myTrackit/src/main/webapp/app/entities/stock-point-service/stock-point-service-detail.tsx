import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './stock-point-service.reducer';

export const StockPointServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const stockPointServiceEntity = useAppSelector(state => state.mytrackit.stockPointService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stockPointServiceDetailsHeading">
          <Translate contentKey="myTrackitApp.stockPointService.detail.title">StockPointService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.id}</dd>
          <dt>
            <span id="stockAnnee">
              <Translate contentKey="myTrackitApp.stockPointService.stockAnnee">Stock Annee</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.stockAnnee}</dd>
          <dt>
            <span id="stockMois">
              <Translate contentKey="myTrackitApp.stockPointService.stockMois">Stock Mois</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.stockMois}</dd>
          <dt>
            <span id="entreeMois">
              <Translate contentKey="myTrackitApp.stockPointService.entreeMois">Entree Mois</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.entreeMois}</dd>
          <dt>
            <span id="sortieMois">
              <Translate contentKey="myTrackitApp.stockPointService.sortieMois">Sortie Mois</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.sortieMois}</dd>
          <dt>
            <span id="stockFinmois">
              <Translate contentKey="myTrackitApp.stockPointService.stockFinmois">Stock Finmois</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.stockFinmois}</dd>
          <dt>
            <span id="stockDebut">
              <Translate contentKey="myTrackitApp.stockPointService.stockDebut">Stock Debut</Translate>
            </span>
          </dt>
          <dd>{stockPointServiceEntity.stockDebut}</dd>
          <dt>
            <Translate contentKey="myTrackitApp.stockPointService.pointService">Point Service</Translate>
          </dt>
          <dd>{stockPointServiceEntity.pointService ? stockPointServiceEntity.pointService.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/stock-point-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/stock-point-service/${stockPointServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StockPointServiceDetail;
