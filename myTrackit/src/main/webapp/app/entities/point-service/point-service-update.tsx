import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointService } from 'app/shared/model/point-service.model';
import { getEntity, updateEntity, createEntity, reset } from './point-service.reducer';

export const PointServiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pointServiceEntity = useAppSelector(state => state.mytrackit.pointService.entity);
  const loading = useAppSelector(state => state.mytrackit.pointService.loading);
  const updating = useAppSelector(state => state.mytrackit.pointService.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.pointService.updateSuccess);

  const handleClose = () => {
    navigate('/point-service');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...pointServiceEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...pointServiceEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.pointService.home.createOrEditLabel" data-cy="PointServiceCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.pointService.home.createOrEditLabel">Create or edit a PointService</Translate>
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
                  id="point-service-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.pointService.nomPos')}
                id="point-service-nomPos"
                name="nomPos"
                data-cy="nomPos"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointService.posLon')}
                id="point-service-posLon"
                name="posLon"
                data-cy="posLon"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointService.posLat')}
                id="point-service-posLat"
                name="posLat"
                data-cy="posLat"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointService.posContact')}
                id="point-service-posContact"
                name="posContact"
                data-cy="posContact"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointService.posGsm')}
                id="point-service-posGsm"
                name="posGsm"
                data-cy="posGsm"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/point-service" replace color="info">
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

export default PointServiceUpdate;
