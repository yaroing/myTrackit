import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISuiviMission } from 'app/shared/model/suivi-mission.model';
import { getEntity, updateEntity, createEntity, reset } from './suivi-mission.reducer';

export const SuiviMissionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const suiviMissionEntity = useAppSelector(state => state.mytrackit.suiviMission.entity);
  const loading = useAppSelector(state => state.mytrackit.suiviMission.loading);
  const updating = useAppSelector(state => state.mytrackit.suiviMission.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.suiviMission.updateSuccess);

  const handleClose = () => {
    navigate('/suivi-mission');
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
      ...suiviMissionEntity,
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
          ...suiviMissionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.suiviMission.home.createOrEditLabel" data-cy="SuiviMissionCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.suiviMission.home.createOrEditLabel">Create or edit a SuiviMission</Translate>
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
                  id="suivi-mission-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.suiviMission.problemeConstate')}
                id="suivi-mission-problemeConstate"
                name="problemeConstate"
                data-cy="problemeConstate"
                type="textarea"
              />
              <ValidatedField
                label={translate('myTrackitApp.suiviMission.actionRecommandee')}
                id="suivi-mission-actionRecommandee"
                name="actionRecommandee"
                data-cy="actionRecommandee"
                type="textarea"
              />
              <ValidatedField
                label={translate('myTrackitApp.suiviMission.dateEcheance')}
                id="suivi-mission-dateEcheance"
                name="dateEcheance"
                data-cy="dateEcheance"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/suivi-mission" replace color="info">
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

export default SuiviMissionUpdate;
