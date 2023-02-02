import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './requete-point-service.reducer';

export const RequetePointServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requetePointServiceEntity = useAppSelector(state => state.mytrackit.requetePointService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requetePointServiceDetailsHeading">
          <Translate contentKey="myTrackitApp.requetePointService.detail.title">RequetePointService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requetePointServiceEntity.id}</dd>
          <dt>
            <span id="stockDisponible">
              <Translate contentKey="myTrackitApp.requetePointService.stockDisponible">Stock Disponible</Translate>
            </span>
          </dt>
          <dd>{requetePointServiceEntity.stockDisponible}</dd>
          <dt>
            <span id="quantDem">
              <Translate contentKey="myTrackitApp.requetePointService.quantDem">Quant Dem</Translate>
            </span>
          </dt>
          <dd>{requetePointServiceEntity.quantDem}</dd>
          <dt>
            <span id="quantTrs">
              <Translate contentKey="myTrackitApp.requetePointService.quantTrs">Quant Trs</Translate>
            </span>
          </dt>
          <dd>{requetePointServiceEntity.quantTrs}</dd>
          <dt>
            <span id="quantRec">
              <Translate contentKey="myTrackitApp.requetePointService.quantRec">Quant Rec</Translate>
            </span>
          </dt>
          <dd>{requetePointServiceEntity.quantRec}</dd>
          <dt>
            <span id="reqTraitee">
              <Translate contentKey="myTrackitApp.requetePointService.reqTraitee">Req Traitee</Translate>
            </span>
          </dt>
          <dd>{requetePointServiceEntity.reqTraitee}</dd>
          <dt>
            <span id="dateReq">
              <Translate contentKey="myTrackitApp.requetePointService.dateReq">Date Req</Translate>
            </span>
          </dt>
          <dd>
            {requetePointServiceEntity.dateReq ? (
              <TextFormat value={requetePointServiceEntity.dateReq} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateRec">
              <Translate contentKey="myTrackitApp.requetePointService.dateRec">Date Rec</Translate>
            </span>
          </dt>
          <dd>
            {requetePointServiceEntity.dateRec ? (
              <TextFormat value={requetePointServiceEntity.dateRec} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTransfert">
              <Translate contentKey="myTrackitApp.requetePointService.dateTransfert">Date Transfert</Translate>
            </span>
          </dt>
          <dd>
            {requetePointServiceEntity.dateTransfert ? (
              <TextFormat value={requetePointServiceEntity.dateTransfert} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="myTrackitApp.requetePointService.pointService">Point Service</Translate>
          </dt>
          <dd>{requetePointServiceEntity.pointService ? requetePointServiceEntity.pointService.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/requete-point-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/requete-point-service/${requetePointServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequetePointServiceDetail;
