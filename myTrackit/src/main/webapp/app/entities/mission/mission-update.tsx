import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMission } from 'app/shared/model/mission.model';
import { getEntity, updateEntity, createEntity, reset } from './mission.reducer';

export const MissionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const missionEntity = useAppSelector(state => state.mytrackit.mission.entity);
  const loading = useAppSelector(state => state.mytrackit.mission.loading);
  const updating = useAppSelector(state => state.mytrackit.mission.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.mission.updateSuccess);

  const handleClose = () => {
    navigate('/mission');
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
    values.dateMission = convertDateTimeToServer(values.dateMission);
    values.dateDebut = convertDateTimeToServer(values.dateDebut);
    values.dateFin = convertDateTimeToServer(values.dateFin);
    values.debutMission = convertDateTimeToServer(values.debutMission);
    values.finMission = convertDateTimeToServer(values.finMission);

    const entity = {
      ...missionEntity,
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
      ? {
          dateMission: displayDefaultDateTime(),
          dateDebut: displayDefaultDateTime(),
          dateFin: displayDefaultDateTime(),
          debutMission: displayDefaultDateTime(),
          finMission: displayDefaultDateTime(),
        }
      : {
          ...missionEntity,
          dateMission: convertDateTimeFromServer(missionEntity.dateMission),
          dateDebut: convertDateTimeFromServer(missionEntity.dateDebut),
          dateFin: convertDateTimeFromServer(missionEntity.dateFin),
          debutMission: convertDateTimeFromServer(missionEntity.debutMission),
          finMission: convertDateTimeFromServer(missionEntity.finMission),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.mission.home.createOrEditLabel" data-cy="MissionCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.mission.home.createOrEditLabel">Create or edit a Mission</Translate>
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
                  id="mission-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.mission.dateMission')}
                id="mission-dateMission"
                name="dateMission"
                data-cy="dateMission"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.mission.dateDebut')}
                id="mission-dateDebut"
                name="dateDebut"
                data-cy="dateDebut"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.mission.dateFin')}
                id="mission-dateFin"
                name="dateFin"
                data-cy="dateFin"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedBlobField
                label={translate('myTrackitApp.mission.rapportMission')}
                id="mission-rapportMission"
                name="rapportMission"
                data-cy="rapportMission"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('myTrackitApp.mission.debutMission')}
                id="mission-debutMission"
                name="debutMission"
                data-cy="debutMission"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.mission.finMission')}
                id="mission-finMission"
                name="finMission"
                data-cy="finMission"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myTrackitApp.mission.field10')}
                id="mission-field10"
                name="field10"
                data-cy="field10"
                type="text"
              />
              <ValidatedField label={translate('myTrackitApp.mission.fin')} id="mission-fin" name="fin" data-cy="fin" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mission" replace color="info">
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

export default MissionUpdate;
