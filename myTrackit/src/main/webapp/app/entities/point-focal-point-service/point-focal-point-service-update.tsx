import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPointFocalPointService } from 'app/shared/model/point-focal-point-service.model';
import { getEntity, updateEntity, createEntity, reset } from './point-focal-point-service.reducer';

export const PointFocalPointServiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pointFocalPointServiceEntity = useAppSelector(state => state.mytrackit.pointFocalPointService.entity);
  const loading = useAppSelector(state => state.mytrackit.pointFocalPointService.loading);
  const updating = useAppSelector(state => state.mytrackit.pointFocalPointService.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.pointFocalPointService.updateSuccess);

  const handleClose = () => {
    navigate('/point-focal-point-service');
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
      ...pointFocalPointServiceEntity,
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
          ...pointFocalPointServiceEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.pointFocalPointService.home.createOrEditLabel" data-cy="PointFocalPointServiceCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.pointFocalPointService.home.createOrEditLabel">
              Create or edit a PointFocalPointService
            </Translate>
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
                  id="point-focal-point-service-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPointService.nomPf')}
                id="point-focal-point-service-nomPf"
                name="nomPf"
                data-cy="nomPf"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPointService.fonctionPf')}
                id="point-focal-point-service-fonctionPf"
                name="fonctionPf"
                data-cy="fonctionPf"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPointService.gsmPf')}
                id="point-focal-point-service-gsmPf"
                name="gsmPf"
                data-cy="gsmPf"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.pointFocalPointService.emailPf')}
                id="point-focal-point-service-emailPf"
                name="emailPf"
                data-cy="emailPf"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/point-focal-point-service" replace color="info">
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

export default PointFocalPointServiceUpdate;
