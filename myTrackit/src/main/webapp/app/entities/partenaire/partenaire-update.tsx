import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPartenaire } from 'app/shared/model/partenaire.model';
import { getEntity, updateEntity, createEntity, reset } from './partenaire.reducer';

export const PartenaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const partenaireEntity = useAppSelector(state => state.mytrackit.partenaire.entity);
  const loading = useAppSelector(state => state.mytrackit.partenaire.loading);
  const updating = useAppSelector(state => state.mytrackit.partenaire.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.partenaire.updateSuccess);

  const handleClose = () => {
    navigate('/partenaire');
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
      ...partenaireEntity,
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
          ...partenaireEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.partenaire.home.createOrEditLabel" data-cy="PartenaireCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.partenaire.home.createOrEditLabel">Create or edit a Partenaire</Translate>
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
                  id="partenaire-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.partenaire.nomPartenaire')}
                id="partenaire-nomPartenaire"
                name="nomPartenaire"
                data-cy="nomPartenaire"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.partenaire.autreNom')}
                id="partenaire-autreNom"
                name="autreNom"
                data-cy="autreNom"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.partenaire.logPhone')}
                id="partenaire-logPhone"
                name="logPhone"
                data-cy="logPhone"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.partenaire.emailPartenaire')}
                id="partenaire-emailPartenaire"
                name="emailPartenaire"
                data-cy="emailPartenaire"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.partenaire.locPartenaire')}
                id="partenaire-locPartenaire"
                name="locPartenaire"
                data-cy="locPartenaire"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/partenaire" replace color="info">
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

export default PartenaireUpdate;
