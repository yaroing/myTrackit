import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointService } from 'app/shared/model/point-service.model';
import { getEntities as getPointServices } from 'app/entities/point-service/point-service.reducer';
import { IRequetePointService } from 'app/shared/model/requete-point-service.model';
import { getEntity, updateEntity, createEntity, reset } from './requete-point-service.reducer';

export const RequetePointServiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pointServices = useAppSelector(state => state.mytrackit.pointService.entities);
  const requetePointServiceEntity = useAppSelector(state => state.mytrackit.requetePointService.entity);
  const loading = useAppSelector(state => state.mytrackit.requetePointService.loading);
  const updating = useAppSelector(state => state.mytrackit.requetePointService.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.requetePointService.updateSuccess);

  const handleClose = () => {
    navigate('/requete-point-service');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPointServices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateReq = convertDateTimeToServer(values.dateReq);
    values.dateRec = convertDateTimeToServer(values.dateRec);
    values.dateTransfert = convertDateTimeToServer(values.dateTransfert);

    const entity = {
      ...requetePointServiceEntity,
      ...values,
      pointService: pointServices.find(it => it.id.toString() === values.pointService.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateReq: displayDefaultDateTime(),
          dateRec: displayDefaultDateTime(),
          dateTransfert: displayDefaultDateTime(),
        }
      : {
          ...requetePointServiceEntity,
          dateReq: convertDateTimeFromServer(requetePointServiceEntity.dateReq),
          dateRec: convertDateTimeFromServer(requetePointServiceEntity.dateRec),
          dateTransfert: convertDateTimeFromServer(requetePointServiceEntity.dateTransfert),
          pointService: requetePointServiceEntity?.pointService?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.requetePointService.home.createOrEditLabel" data-cy="RequetePointServiceCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.requetePointService.home.createOrEditLabel">Create or edit a RequetePointService</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="requete-point-service-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.stockDisponible')}
                id="requete-point-service-stockDisponible"
                name="stockDisponible"
                data-cy="stockDisponible"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.quantDem')}
                id="requete-point-service-quantDem"
                name="quantDem"
                data-cy="quantDem"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.quantTrs')}
                id="requete-point-service-quantTrs"
                name="quantTrs"
                data-cy="quantTrs"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.quantRec')}
                id="requete-point-service-quantRec"
                name="quantRec"
                data-cy="quantRec"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.reqTraitee')}
                id="requete-point-service-reqTraitee"
                name="reqTraitee"
                data-cy="reqTraitee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.dateReq')}
                id="requete-point-service-dateReq"
                name="dateReq"
                data-cy="dateReq"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.dateRec')}
                id="requete-point-service-dateRec"
                name="dateRec"
                data-cy="dateRec"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.requetePointService.dateTransfert')}
                id="requete-point-service-dateTransfert"
                name="dateTransfert"
                data-cy="dateTransfert"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="requete-point-service-pointService"
                name="pointService"
                data-cy="pointService"
                label={translate('myTrackitApp.requetePointService.pointService')}
                type="select"
              >
                <option value="" key="0" />
                {pointServices
                  ? pointServices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/requete-point-service" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RequetePointServiceUpdate;
