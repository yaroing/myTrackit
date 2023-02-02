import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRequetePartenaire } from 'app/shared/model/requete-partenaire.model';
import { getEntities as getRequetePartenaires } from 'app/entities/requete-partenaire/requete-partenaire.reducer';
import { IDetailsRequete } from 'app/shared/model/details-requete.model';
import { getEntity, updateEntity, createEntity, reset } from './details-requete.reducer';

export const DetailsRequeteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const requetePartenaires = useAppSelector(state => state.mytrackit.requetePartenaire.entities);
  const detailsRequeteEntity = useAppSelector(state => state.mytrackit.detailsRequete.entity);
  const loading = useAppSelector(state => state.mytrackit.detailsRequete.loading);
  const updating = useAppSelector(state => state.mytrackit.detailsRequete.updating);
  const updateSuccess = useAppSelector(state => state.mytrackit.detailsRequete.updateSuccess);

  const handleClose = () => {
    navigate('/details-requete');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRequetePartenaires({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...detailsRequeteEntity,
      ...values,
      requetePartenaire: requetePartenaires.find(it => it.id.toString() === values.requetePartenaire.toString()),
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
          ...detailsRequeteEntity,
          requetePartenaire: detailsRequeteEntity?.requetePartenaire?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myTrackitApp.detailsRequete.home.createOrEditLabel" data-cy="DetailsRequeteCreateUpdateHeading">
            <Translate contentKey="myTrackitApp.detailsRequete.home.createOrEditLabel">Create or edit a DetailsRequete</Translate>
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
                  id="details-requete-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myTrackitApp.detailsRequete.quantiteDemandee')}
                id="details-requete-quantiteDemandee"
                name="quantiteDemandee"
                data-cy="quantiteDemandee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.detailsRequete.quantiteApprouvee')}
                id="details-requete-quantiteApprouvee"
                name="quantiteApprouvee"
                data-cy="quantiteApprouvee"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.detailsRequete.quantiteRecue')}
                id="details-requete-quantiteRecue"
                name="quantiteRecue"
                data-cy="quantiteRecue"
                type="text"
              />
              <ValidatedField
                label={translate('myTrackitApp.detailsRequete.itemObs')}
                id="details-requete-itemObs"
                name="itemObs"
                data-cy="itemObs"
                type="textarea"
              />
              <ValidatedField
                id="details-requete-requetePartenaire"
                name="requetePartenaire"
                data-cy="requetePartenaire"
                label={translate('myTrackitApp.detailsRequete.requetePartenaire')}
                type="select"
              >
                <option value="" key="0" />
                {requetePartenaires
                  ? requetePartenaires.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/details-requete" replace color="info">
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

export default DetailsRequeteUpdate;
