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
import { IMonitoring } from 'app/shared/model/monitoring.model';
import { getEntity, updateEntity, createEntity, reset } from './monitoring.reducer';

export const MonitoringUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pointServices = useAppSelector(state => state.mytrackit.pointService.entities);
  const monitoringEntity = useAppSelector(state => state.mytrackit.monitoring.entity);
  const loading = useAppSelector(state => state.mytrackit.monitoring.loading);
  const updating = useAppSelector(state => state.mytrackit.monitoring.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.monitoring.updateSuccess);

  const handleClose = () => {
    navigate('/monitoring');
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
    const entity = {
      ...monitoringEntity,
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
      ? {}
      : {
          ...monitoringEntity,
          pointService: monitoringEntity?.pointService?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.monitoring.home.createOrEditLabel" data-cy="MonitoringCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.monitoring.home.createOrEditLabel">Create or edit a Monitoring</Translate>
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
                  id="monitoring-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeAnnee')}
                id="monitoring-atpeAnnee"
                name="atpeAnnee"
                data-cy="atpeAnnee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeMois')}
                id="monitoring-atpeMois"
                name="atpeMois"
                data-cy="atpeMois"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeStock')}
                id="monitoring-atpeStock"
                name="atpeStock"
                data-cy="atpeStock"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeDispo')}
                id="monitoring-atpeDispo"
                name="atpeDispo"
                data-cy="atpeDispo"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeEndom')}
                id="monitoring-atpeEndom"
                name="atpeEndom"
                data-cy="atpeEndom"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpePerime')}
                id="monitoring-atpePerime"
                name="atpePerime"
                data-cy="atpePerime"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeRupture')}
                id="monitoring-atpeRupture"
                name="atpeRupture"
                data-cy="atpeRupture"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeNjour')}
                id="monitoring-atpeNjour"
                name="atpeNjour"
                data-cy="atpeNjour"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeMagasin')}
                id="monitoring-atpeMagasin"
                name="atpeMagasin"
                data-cy="atpeMagasin"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpePalette')}
                id="monitoring-atpePalette"
                name="atpePalette"
                data-cy="atpePalette"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpePosition')}
                id="monitoring-atpePosition"
                name="atpePosition"
                data-cy="atpePosition"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeHauteur')}
                id="monitoring-atpeHauteur"
                name="atpeHauteur"
                data-cy="atpeHauteur"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpePersonnel')}
                id="monitoring-atpePersonnel"
                name="atpePersonnel"
                data-cy="atpePersonnel"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeAdmission')}
                id="monitoring-atpeAdmission"
                name="atpeAdmission"
                data-cy="atpeAdmission"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeSortie')}
                id="monitoring-atpeSortie"
                name="atpeSortie"
                data-cy="atpeSortie"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeGueris')}
                id="monitoring-atpeGueris"
                name="atpeGueris"
                data-cy="atpeGueris"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeAbandon')}
                id="monitoring-atpeAbandon"
                name="atpeAbandon"
                data-cy="atpeAbandon"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpePoids')}
                id="monitoring-atpePoids"
                name="atpePoids"
                data-cy="atpePoids"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeTrasnsfert')}
                id="monitoring-atpeTrasnsfert"
                name="atpeTrasnsfert"
                data-cy="atpeTrasnsfert"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.monitoring.atpeParent')}
                id="monitoring-atpeParent"
                name="atpeParent"
                data-cy="atpeParent"
                type="text"
              />
              <ValidatedField
                id="monitoring-pointService"
                name="pointService"
                data-cy="pointService"
                label={translate('myTrackitApp.monitoring.pointService')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/monitoring" replace color="info">
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

export default MonitoringUpdate;
